function openPublisherModal() {
    const publisherModal = document.getElementById('publisherModal');
    const errorMessage = document.getElementById('error_message');
    const publisherForm = document.getElementById('publisher_form');

    errorMessage.classList.add('d-none');

    // $(publisherModal).on('show.bs.modal', function () {
    // });

    publisherForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(publisherForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

        fetch('/admin/publisher', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            },
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('출판사 등록 실패');
                }
                return response.json();
            })
            .then(data => {
                console.log('출판사 등록 성공:', data);
                alert('출판사가 성공적으로 등록되었습니다.');
                $('#publisherModal').modal('hide');
                refreshPublisherList();
            })
            .catch(error => {
                console.error('출판사 등록 오류:', error);
                alert('이미 존재하는 출판사 입니다.');
            });
    });

    const modal = new bootstrap.Modal(publisherModal);
    modal.show();
}

function openEditModal(publisherId) {
    const editPublisherModal = document.getElementById('editPublisherModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_publisher_form');

    errorMessage.classList.add('d-none');

    editPublisherModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        publisherId = button.getAttribute('data-id');
        const publisherName = button.getAttribute('data-name');

        document.getElementById('edit_publisher_id').value = publisherId;
        document.getElementById('edit_name').value = publisherName;
    });

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(editForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const updateName = document.getElementById('edit_name').value;
        const data = {
            name: updateName,
            _method: 'PUT'
        };

        fetch(`/admin/publisher/edit/` + publisherId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 409) {
                    return response.json().then(data => {
                        throw new Error(data.error || '이미 존재하는 출판사 이름입니다.');
                    });
                }
                throw new Error('서버 오류');
            })
            .then(data => {
                console.log('출판사 수정 성공:', data);
                alert("출판사 수정 성공");

                $('#editPublisherModal').modal('hide');
                refreshPublisherList();

            })
            .catch(error => {
                console.error('수정 오류:', error);
                alert('이미 존재하는 출판사 입니다.');
                // document.getElementById('error_message').style.display = 'block';
                // document.getElementById('error_message').innerText = error.message;
            });
    });
}

function handleDeletePublisher(publisherId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const deleteUrl = `/admin/publisher/delete/${publisherId}`;

    const confirmDeleteButton = document.getElementById('confirmDelete');
    confirmDeleteButton.onclick = function () {
        fetch(deleteUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify({_method: 'DELETE'})
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버에서 오류 발생');
                }

                if (response.status === 204) {
                    const publisherElement = document.getElementById(`publisher-${publisherId}`);
                    if (publisherElement) {
                        publisherElement.remove();
                    }
                    alert('삭제되었습니다.');
                } else {
                    return response.json().then(data => {
                        console.log(data);
                        alert('삭제되었습니다.');
                    });
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                // alert('삭제 실패: ' + error.message);
            });

        $('#deletePublisherModal').modal('hide');
        refreshPublisherList();
    };

    const deleteModal = new bootstrap.Modal(document.getElementById('deletePublisherModal'));
    deleteModal.show();
}

function refreshPublisherList() {
    $("#publisher").load(window.location.href + " #publisher");
}

function openContributorModal() {
    const contributorModal = document.getElementById('contributorModal');
    const errorMessage = document.getElementById('error_message');
    const contributorForm = document.getElementById('contributor_form');

    errorMessage.classList.add('d-none');

    // $(contributorModal).on('show.bs.modal', function () {
    // });

    contributorForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(contributorForm);

        fetch('/admin/contributor', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('기여자 등록 실패');
                }
                return response.json();
            })
            .then(data => {
                console.log('기여자 등록 성공:', data);
                alert('기여자가 성공적으로 등록되었습니다.');
                $('#contributorModal').modal('hide');
                refreshContributorList();
            })
            .catch(error => {
                console.error('기여자 등록 오류:', error);
                alert('다시 작성해 주세요.');
            });
    });

    const modal = new bootstrap.Modal(contributorModal);
    modal.show();
}

function openEditModal(contributorId) {
    const editContributorModal = document.getElementById('editContributorModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_contributor_form');

    errorMessage.classList.add('d-none');

    editContributorModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        contributorId = button.getAttribute('data-id');
        const contributorName = button.getAttribute('data-name');

        document.getElementById('edit_contributor_id').value = contributorId;
        document.getElementById('edit_name').value = contributorName;
    });

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(editForm);
        const updateName = document.getElementById('edit_name').value;
        const data = {
            name: updateName
        };

        fetch(`/admin/contributor/` + contributorId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 409) {
                    return response.json().then(data => {
                        throw new Error(data.error || '이미 존재하는 기여자 이름입니다.');
                    });
                }
                throw new Error('서버 오류');
            })
            .then(data => {
                console.log('기여자 수정 성공:', data);
                alert("기여자 수정 성공");

                $('#editContributorModal').modal('hide');
                refreshContributorList();

                document.getElementById('edit_name').value = '';
            })
            .catch(error => {
                console.error('수정 오류:', error);
                document.getElementById('error_message').style.display = 'block';
                document.getElementById('error_message').innerText = error.message;

                document.getElementById('edit_name').value = '';
            });
    });
}

function handleDeleteContributor(contributorId) {
    const deleteUrl = `/admin/contributor/${contributorId}`;

    const confirmDeleteButton = document.getElementById('confirmDelete');
    confirmDeleteButton.onclick = function () {
        fetch(deleteUrl, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({_method: 'POST'})
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버에서 오류 발생');
                }

                if (response.status === 204) {
                    const contributorElement = document.getElementById(`contributor-${contributorId}`);
                    if (contributorElement) {
                        contributorElement.remove();
                    }
                    alert('삭제되었습니다.');
                } else {
                    return response.json().then(data => {
                        console.log(data);
                        alert('삭제되었습니다.');
                        $('#deleteContributorModal').modal('hide');
                        refreshContributorList();
                    });
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 실패: ' + error.message);
            });


    };

    const deleteModal = new bootstrap.Modal(document.getElementById('deleteContributorModal'));
    deleteModal.show();
}

function refreshContributorList() {
    $("#contributor").load(window.location.href + " #contributor");
}

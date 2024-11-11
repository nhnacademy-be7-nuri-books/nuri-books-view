function openTagModal() {
    const tagModal = document.getElementById('tagModal');
    const errorMessage = document.getElementById('error_message');
    const tagForm = document.getElementById('tag_form');

    errorMessage.classList.add('d-none');

    // $(tagModal).on('show.bs.modal', function () {
    // });

    tagForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(tagForm);

        fetch('/admin/tag', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('태그 등록 실패');
                }
                return response.json();
            })
            .then(data => {
                console.log('태그 등록 성공:', data);
                alert('태그가 성공적으로 등록되었습니다.');
                $('#tagModal').modal('hide');
                refreshTagList();
            })
            .catch(error => {
                console.error('태그 등록 오류:', error);
                alert('이미 존재하는 태그 입니다.');
            });
    });

    const modal = new bootstrap.Modal(tagModal);
    modal.show();
}

function openEditModal(tagId) {
    const editTagModal = document.getElementById('editTagModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_tag_form');

    errorMessage.classList.add('d-none');

    editTagModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        tagId = button.getAttribute('data-id');
        const tagName = button.getAttribute('data-name');

        document.getElementById('edit_tag_id').value = tagId;
        document.getElementById('edit_name').value = tagName;
    });

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(editForm);
        const updateName = document.getElementById('edit_name').value;
        const data = {
            name: updateName,
            _method: 'PUT'
        };

        fetch(`/admin/tag/edit/` + tagId, {
            method: 'POST',
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
                        throw new Error(data.error || '이미 존재하는 태그 이름입니다.');
                    });
                }
                throw new Error('서버 오류');
            })
            .then(data => {
                console.log('태그 수정 성공:', data);
                alert("태그 수정 성공");

                $('#editTagModal').modal('hide');
                refreshTagList();

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

function handleDeleteTag(tagId) {
    const deleteUrl = `/admin/tag/delete/${tagId}`;

    const confirmDeleteButton = document.getElementById('confirmDelete');
    confirmDeleteButton.onclick = function () {
        fetch(deleteUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({_method: 'DELETE'})
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버에서 오류 발생');
                }

                if (response.status === 204) {
                    const tagElement = document.getElementById(`tag-${tagId}`);
                    if (tagElement) {
                        tagElement.remove();
                    }
                    alert('삭제되었습니다.');
                } else {
                    return response.json().then(data => {
                        console.log(data);
                        alert('삭제되었습니다.');
                        $('#deleteTagModal').modal('hide');
                        refreshTagList();
                    });
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 실패: ' + error.message);
            });


    };

    const deleteModal = new bootstrap.Modal(document.getElementById('deleteTagModal'));
    deleteModal.show();
}

function refreshTagList() {
    $("#tag").load(window.location.href + " #tag");
}

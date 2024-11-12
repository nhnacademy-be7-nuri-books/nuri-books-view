function openRegisterModal(url) {
    const modal = document.getElementById('register_modal');
    const errorMessage = document.getElementById('error_message');
    const form = document.getElementById('register_form');

    errorMessage.classList.add('d-none');
    const bootModal = new bootstrap.Modal(modal);

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        console.log(formData)
        fetch(url, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('등록 실패');
                }
                return response.json();
            })
            .then(data => {
                alert('성공적으로 등록되었습니다.');
                bootModal.hide('hide');
                refreshList();
            })
            .catch(error => {
                console.error('출판사 등록 오류:', error);
                errorMessage.show('이미 존재하는 출판사 입니다.');
            });
    });

    bootModal.show();
}

function openEditModal(pointPolicyId) {
    const editPointPolicyModal = document.getElementById('editPointPolicyModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_pointPolicy_form');

    errorMessage.classList.add('d-none');

    editPointPolicyModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        pointPolicyId = button.getAttribute('data-id');
        const pointPolicyName = button.getAttribute('data-name');

        document.getElementById('edit_pointPolicy_id').value = pointPolicyId;
        document.getElementById('edit_name').value = pointPolicyName;
    });

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(editForm);
        const updateName = document.getElementById('edit_name').value;
        const data = {
            name: updateName,
            _method: 'PUT'
        };

        fetch(`/admin/pointPolicy/edit/` + pointPolicyId, {
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
                        throw new Error(data.error || '이미 존재하는 출판사 이름입니다.');
                    });
                }
                throw new Error('서버 오류');
            })
            .then(data => {
                console.log('출판사 수정 성공:', data);
                alert("출판사 수정 성공");

                $('#editPointPolicyModal').modal('hide');
                refreshPointPolicyList();

            })
            .catch(error => {
                console.error('수정 오류:', error);
                // document.getElementById('error_message').style.display = 'block';
                // document.getElementById('error_message').innerText = error.message;
            });
    });
}

function handleDeletePointPolicy(url) {

    const confirmDeleteButton = document.getElementById('confirmDelete');
    confirmDeleteButton.onclick = function () {
        fetch(url, {
            method: 'DELETE',
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
                    const pointPolicyElement = document.getElementById(`pointPolicy-${pointPolicyId}`);
                    if (pointPolicyElement) {
                        pointPolicyElement.remove();
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

        $('#deletePointPolicyModal').modal('hide');
        refreshPointPolicyList();
    };

    const deleteModal = new bootstrap.Modal(document.getElementById('deletePointPolicyModal'));
    deleteModal.show();
}

function refreshList() {
    $("#pointPolicy").load(window.location.href + " #pointPolicy");
}
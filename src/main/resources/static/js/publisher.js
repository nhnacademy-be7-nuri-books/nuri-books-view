function openEditModal(publisherId) {
    const editPublisherModal = document.getElementById('editPublisherModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_publisher_form');

    // 모달 열기 전에 초기화
    errorMessage.classList.add('d-none');  // 에러 메시지 숨기기

    // 모달이 열릴 때마다 실행되는 이벤트 리스너 등록
    editPublisherModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const publisherId = button.getAttribute('data-id');
        const publisherName = button.getAttribute('data-name');

        document.getElementById('edit_publisher_id').value = publisherId;
        document.getElementById('edit_name').value = publisherName;
    });

    // 폼 제출 시 비동기 요청
    editForm.addEventListener('submit', function (event) {
        event.preventDefault();  // 폼 기본 제출을 방지

        const formData = new FormData(editForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const updateName = document.getElementById('edit_name').value
        const data = {
            name: updateName,
            _method: 'PUT'
        }
        // 서버에 POST 요청
        fetch(`/admin/publisher/edit/` + publisherId, {
            method: 'POST',  // 실제로는 POST 요청을 사용
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken  // CSRF 토큰
            },
            body: JSON.stringify(data)
            // 폼에서 'name' 값 추가

        })
            .then(response => {
                if (!response.ok) {
                    if (response.status === 409) {
                        // 409 Conflict 발생 시, 에러 메시지 표시
                        errorMessage.classList.remove('d-none');
                    } else {
                        // 다른 에러 처리 (예: 500 Internal Server Error 등)
                        alert('다시 시도해주세요.');
                    }
                } else {
                    // 성공적인 응답 후 모달 닫기 등
                    alert('수정되었습니다.');
                    // location.reload();
                    window.location.href = '/admin/publisher';  // 수정 후 목록 페이지로 리디렉션

                    $('#editPublisherModal').modal('hide');
                }
            })
            .catch(error => {
                // 네트워크 에러 처리
                alert('네트워크 오류입니다. 다시 시도해주세요.');
            });
    });
}

function handleDeletePublisher(publisherId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content; // CSRF 토큰 가져오기
    const deleteUrl = `/admin/publisher/delete/${publisherId}`;

    // 삭제 모달 확인 버튼 클릭 시 실행되는 부분
    const confirmDeleteButton = document.getElementById('confirmDelete');
    confirmDeleteButton.onclick = function () {
        fetch(deleteUrl, {
            method: 'POST',  // 실제로는 POST 요청을 사용
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken  // CSRF 토큰
            },
            body: JSON.stringify({_method: 'DELETE'})  // 실제 메서드는 DELETE로 처리하도록 서버에 명시
        })
            .then(response => {
                if (response.ok) {
                    alert('삭제되었습니다.');
                    // location.reload();  // 페이지 새로고침
                    window.location.href = '/admin/publisher';  // 수정 후 목록 페이지로 리디렉션

                } else {
                    alert('삭제 실패. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('네트워크 오류가 발생했습니다.');
            });

        // 모달 닫기
        $('#deletePublisherModal').modal('hide');
    };

    // 삭제 모달을 띄우는 부분
    var deleteModal = new bootstrap.Modal(document.getElementById('deletePublisherModal'));
    deleteModal.show();
}

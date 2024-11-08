function openTagModal() {
    const tagModal = document.getElementById('tagModal');
    const errorMessage = document.getElementById('error_message');
    const tagForm = document.getElementById('tag_form');

    // 모달 열기 전에 초기화
    errorMessage.classList.add('d-none');  // 에러 메시지 숨기기

    // Bootstrap 모달 이벤트 리스너 등록
    $(tagModal).on('show.bs.modal', function () {
        // 모달이 열릴 때 실행되는 코드
        // 예를 들어, 모달이 열릴 때 폼을 초기화하거나 다른 설정을 할 수 있습니다.
    });

    // 폼 제출 이벤트 리스너 등록
    tagForm.addEventListener('submit', function (event) {
        event.preventDefault();  // 폼 기본 제출을 방지

        // 폼 데이터 가져오기
        const formData = new FormData(tagForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

        // 서버에 POST 요청
        fetch('/admin/tag', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            },
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
                $('#tagModal').modal('hide'); // 모달 닫기
                // 추가적인 처리가 필요한 경우 여기에 작성
            })
            .catch(error => {
                console.error('태그 등록 오류:', error);
                alert('이미 존재하는 태그 입니다.');
            });
    });

    // Bootstrap 모달 생성 및 보이기
    const modal = new bootstrap.Modal(tagModal);
    modal.show();
}

function openEditModal(tagId) {
    const editTagModal = document.getElementById('editTagModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_tag_form');

    // 모달 열기 전에 초기화
    errorMessage.classList.add('d-none');  // 에러 메시지 숨기기

    // 모달이 열릴 때마다 실행되는 이벤트 리스너 등록
    editTagModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        tagId = button.getAttribute('data-id');
        const tagName = button.getAttribute('data-name');

        document.getElementById('edit_tag_id').value = tagId;
        document.getElementById('edit_name').value = tagName;
    });

    // 폼 제출 시 비동기 요청
    editForm.addEventListener('submit', function (event) {
        event.preventDefault();  // 폼 기본 제출을 방지

        const formData = new FormData(editForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const updateName = document.getElementById('edit_name').value;
        const data = {
            name: updateName,
            _method: 'PUT'
        };

        // 서버에 POST 요청
        fetch(`/admin/tag/edit/` + tagId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();  // 수정이 성공하면 응답을 JSON으로 받음
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

                // 수정 후 모달 닫기
                $('#editTagModal').modal('hide');

                // 필드 초기화
                document.getElementById('edit_name').value = '';  // 이름 필드 초기화
            })
            .catch(error => {
                console.error('수정 오류:', error);
                document.getElementById('error_message').style.display = 'block';
                document.getElementById('error_message').innerText = error.message;

                // 오류 후 모달 창을 닫지 않음
                // 필드 초기화 (수정하려는 태그 이름이 그대로 남지 않도록)
                document.getElementById('edit_name').value = '';
            });
    });
}

function handleDeleteTag(tagId) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content; // CSRF 토큰 가져오기
    const deleteUrl = `/admin/tag/delete/${tagId}`;

    // 삭제 모달 확인 버튼 클릭 시 실행되는 부분
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
                // 응답이 OK인지 확인
                if (!response.ok) {
                    throw new Error('서버에서 오류 발생');
                }

                // 204 No Content 상태이면 본문이 없기 때문에 바로 처리
                if (response.status === 204) {
                    const tagElement = document.getElementById(`tag-${tagId}`);
                    if (tagElement) {
                        tagElement.remove();
                    }
                    alert('삭제되었습니다.');
                } else {
                    // 본문이 있는 경우 처리 (예: 200 OK, 201 CREATED 등)
                    return response.json().then(data => {
                        // 응답 데이터가 있다면 처리
                        console.log(data);
                        alert('삭제되었습니다.');
                    });
                }
            })
            .catch(error => {
                console.error('삭제 중 오류 발생:', error);
                alert('삭제 실패: ' + error.message);
            });

        // 모달 닫기
        $('#deleteTagModal').modal('hide');
    };

    // 삭제 모달을 띄우는 부분
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteTagModal'));
    deleteModal.show();
}



// publisher.js 파일

function openPublisherModal() {
    const publisherModal = document.getElementById('publisherModal');
    const errorMessage = document.getElementById('error_message');
    const publisherForm = document.getElementById('publisher_form');

    // 모달 열기 전에 초기화
    errorMessage.classList.add('d-none');  // 에러 메시지 숨기기

    // Bootstrap 모달 이벤트 리스너 등록
    $(publisherModal).on('show.bs.modal', function () {
        // 모달이 열릴 때 실행되는 코드
        // 예를 들어, 모달이 열릴 때 폼을 초기화하거나 다른 설정을 할 수 있습니다.
    });

    // 폼 제출 이벤트 리스너 등록
    publisherForm.addEventListener('submit', function (event) {
        event.preventDefault();  // 폼 기본 제출을 방지

        // 폼 데이터 가져오기
        const formData = new FormData(publisherForm);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

        // 서버에 POST 요청
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
                $('#publisherModal').modal('hide'); // 모달 닫기
                // 추가적인 처리가 필요한 경우 여기에 작성
            })
            .catch(error => {
                console.error('출판사 등록 오류:', error);
                alert('이미 존재하는 출판사 입니다.');
            });
    });

    // Bootstrap 모달 생성 및 보이기
    const modal = new bootstrap.Modal(publisherModal);
    modal.show();
}

function openEditModal(publisherId) {
    const editPublisherModal = document.getElementById('editPublisherModal');
    const errorMessage = document.getElementById('error_message');
    const editForm = document.getElementById('edit_publisher_form');

    // 모달 열기 전에 초기화
    errorMessage.classList.add('d-none');  // 에러 메시지 숨기기

    // 모달이 열릴 때마다 실행되는 이벤트 리스너 등록
    editPublisherModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        publisherId = button.getAttribute('data-id');
        const publisherName = button.getAttribute('data-name');

        document.getElementById('edit_publisher_id').value = publisherId;
        document.getElementById('edit_name').value = publisherName;
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
                    return response.json();  // 수정이 성공하면 응답을 JSON으로 받음
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

                // 수정 후 모달 닫기
                $('#editPublisherModal').modal('hide');

                // 필드 초기화
                document.getElementById('edit_name').value = '';  // 이름 필드 초기화
            })
            .catch(error => {
                console.error('수정 오류:', error);
                document.getElementById('error_message').style.display = 'block';
                document.getElementById('error_message').innerText = error.message;

                // 오류 후 모달 창을 닫지 않음
                // 필드 초기화 (수정하려는 출판사 이름이 그대로 남지 않도록)
                document.getElementById('edit_name').value = '';
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
                    const publisherElement = document.getElementById(`publisher-${publisherId}`);
                    if (publisherElement) {
                        publisherElement.remove();
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
        $('#deletePublisherModal').modal('hide');
    };

    // 삭제 모달을 띄우는 부분
    const deleteModal = new bootstrap.Modal(document.getElementById('deletePublisherModal'));
    deleteModal.show();
}

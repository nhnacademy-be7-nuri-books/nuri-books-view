document.addEventListener("DOMContentLoaded", function () {
    const modalElement = document.getElementById('addressModal');
    const modal = new bootstrap.Modal(modalElement);

    // 모달이 열릴 때 외부 콘텐츠 비활성화
    modalElement.addEventListener('show.bs.modal', function () {
        document.body.style.pointerEvents = 'none';  // 배경 클릭 비활성화
        const modalBackdrop = document.querySelector('.modal-backdrop');
        if (modalBackdrop) {
            modalBackdrop.style.pointerEvents = 'auto';  // 모달 배경 클릭 가능
        }

        // aria-hidden 설정: 모달 외부 콘텐츠 숨김
        document.body.setAttribute('aria-hidden', 'true');
    });

    // 모달이 닫힐 때 외부 콘텐츠 다시 활성화
    modalElement.addEventListener('hidden.bs.modal', function () {
        document.body.style.pointerEvents = '';  // 외부 콘텐츠 클릭 가능
        const modalBackdrop = document.querySelector('.modal-backdrop');
        if (modalBackdrop) {
            modalBackdrop.style.pointerEvents = '';  // 배경 클릭 비활성화
        }

        // aria-hidden 제거: 모달 외부 콘텐츠 복원
        document.body.removeAttribute('aria-hidden');
    });

    // "선택" 버튼 클릭 시 동작
    document.querySelectorAll(".select-address-btn").forEach(function (button) {
        button.addEventListener("click", function () {
            const row = button.closest("tr");

            const addressZipcode = row.querySelector(".zipcode").textContent;
            const addressAddress = row.querySelector(".address").textContent;
            const addressDetailAddress = row.querySelector(".detail-address").textContent;

            // 부모 페이지의 입력 칸에 해당 값을 넣기
            document.getElementById("shipping-zipcode").value = addressZipcode;
            document.getElementById("shipping-address").value = addressAddress;
            document.getElementById("shipping-address-detail").value = addressDetailAddress;

            // 모달 닫기
            modal.hide();
        });
    });
});

function updatePackagingInfo(selectedItem) {

    const selectedWrapping = {
        title: selectedItem.getAttribute('data-title'),  // 포장지 제목
        imageUrl: selectedItem.getAttribute('data-image-url'),  // 포장지 이미지 URL
        price: selectedItem.getAttribute('data-price'),  // 포장지 가격
        id: selectedItem.getAttribute('data-paper-id')  // 포장지 ID
    };

    document.getElementById("dropdownMenuButton").textContent = selectedWrapping.title;
    document.getElementById("packagingTitle").textContent = selectedWrapping.title;
    document.getElementById("packagingId").value = selectedWrapping.id;
    document.getElementById("wrapping-amount").textContent = selectedWrapping.price;

    calculateTotalPrice();
}

function handleNoWrappingSelection() {

    document.getElementById("dropdownMenuButton").textContent = "선택하지 않음";
    document.getElementById("packagingTitle").textContent = "선택하지 않음";
    document.getElementById("packagingId").value = null;
    document.getElementById("wrapping-amount").textContent = "0";

    calculateTotalPrice();

}

function confirmWrapping() {
    // 선택된 책들의 ID를 수집
    const selectedBooks = [];
    const checkboxes = document.querySelectorAll("input[name='selectedBooks']:checked");
    checkboxes.forEach(checkbox => {
        selectedBooks.push(checkbox.value);
    });

    // 선택된 책이 없다면 포장지 정보를 "선택하지 않음"으로 표시하고 종료
    if (selectedBooks.length === 0) {
        handleNoWrappingSelection();

        const modal = bootstrap.Modal.getInstance(document.getElementById('wrappingModal'));
        modal.hide();

        return;
    }

    if (document.getElementById("packagingTitle").textContent !== "선택하지 않음") {
        // 숨겨진 input 필드에 선택된 책들 저장
        document.getElementById("selectedBooksHidden").value = selectedBooks.join(',');
    }

    // 모달 닫기
    const modal = bootstrap.Modal.getInstance(document.getElementById('wrappingModal'));
    modal.hide();
}

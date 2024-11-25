document.addEventListener("DOMContentLoaded", function () {
    // 좋아요 취소 버튼 클릭 이벤트
    const likeButtons = document.querySelectorAll(".like-button");

    likeButtons.forEach(button => {
        button.addEventListener("click", () => {
            const bookId = button.getAttribute("data-book-id");

            fetch(`/view/books/likes/${bookId}`, {
                method: "DELETE",
            })
                .then(response => {
                    if (response.ok) {
                        alert("좋아요가 취소되었습니다.");
                        // 버튼을 비활성화하거나 DOM에서 제거
                        button.closest(".book-item").remove();
                    } else {
                        alert("좋아요 취소에 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("좋아요 취소 중 오류가 발생했습니다.");
                });
        });
    });
});
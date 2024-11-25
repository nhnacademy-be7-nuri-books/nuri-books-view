document.addEventListener('DOMContentLoaded', function () {
    const likeButton = document.getElementById('like-button');

    if (likeButton) {
        const likeCountSpan = document.getElementById('like-count');
        const bookId = likeButton.dataset.bookId;

        // 좋아요 상태 초기화
        fetch(`/view/books/likes/status/${bookId}`)
            .then(response => response.json())
            .then(data => {
                likeButton.dataset.liked = data.isLiked;
                likeButton.querySelector('i').classList.toggle('liked', data.isLiked);
            })
            .catch(error => console.error('초기 상태 불러오기 실패:', error));

        // 좋아요/취소 클릭 이벤트 처리
        likeButton.addEventListener('click', function () {
            const url = `/view/books/likes/${bookId}`;
            const method = likeButton.dataset.liked === 'true' ? 'DELETE' : 'POST';

            fetch(url, { method })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('좋아요 처리 실패');
                    }
                    // 카운트 업데이트
                    const currentCount = parseInt(likeCountSpan.textContent, 10);
                    const newCount = method === 'POST' ? currentCount + 1 : currentCount - 1;

                    likeCountSpan.textContent = newCount;
                    likeButton.dataset.liked = method === 'POST';
                    likeButton.querySelector('i').classList.toggle('liked', method === 'POST');
                })
                .catch(error => console.error('좋아요 처리 오류:', error));
        });
    }
});

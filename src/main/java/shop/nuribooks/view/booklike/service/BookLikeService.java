package shop.nuribooks.view.booklike.service;

import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.booklike.dto.BookLikeResponse;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;
import shop.nuribooks.view.common.dto.PagedResponse;

public interface BookLikeService {
	void addLike(Long bookId);

	void removeLike(Long bookId);

	PagedResponse<BookLikeResponse> getLikedBooks(Pageable pageable);

	LikeStatusResponse getLikeStatus(Long bookId);
}

package shop.nuribooks.view.booklike.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.booklike.dto.BookLikeResponse;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;
import shop.nuribooks.view.booklike.feign.BookLikeServiceClient;
import shop.nuribooks.view.booklike.service.BookLikeService;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Service
public class BookLikeServiceImpl implements BookLikeService {
	private final BookLikeServiceClient bookLikeServiceClient;

	@Override
	public void addLike(Long bookId) {
		bookLikeServiceClient.addLike(bookId);
	}

	@Override
	public void removeLike(Long bookId) {
		bookLikeServiceClient.removeLike(bookId);
	}

	@Override
	public Page<BookLikeResponse> getLikedBooks(Pageable pageable) {
		return bookLikeServiceClient.getBookLikes(pageable.getPageNumber(), pageable.getPageSize());
	}

	@Override
	public LikeStatusResponse getLikeStatus(Long bookId) {
		return bookLikeServiceClient.getLikeStatus(bookId);
	}
}

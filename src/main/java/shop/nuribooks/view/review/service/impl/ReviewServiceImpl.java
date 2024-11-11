package shop.nuribooks.view.review.service.impl;

import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.response.ReviewBookResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.service.ReviewService;

public class ReviewServiceImpl implements ReviewService {
	@Override
	public PagedResponse<ReviewMemberResponse> getReviewsByBookId(long bookId, Pageable pageable) {
		return null;
	}

	@Override
	public PagedResponse<ReviewBookResponse> getReviewsByMemberId(long memberId, Pageable pageable) {
		return null;
	}

	@Override
	public void registerReview(ReviewRequest reviewRequest) {

	}

	@Override
	public void updateReview(ReviewRequest reviewRequest) {

	}
}

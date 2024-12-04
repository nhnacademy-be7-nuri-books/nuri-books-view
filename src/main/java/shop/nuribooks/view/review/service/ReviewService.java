package shop.nuribooks.view.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.request.ReviewUpdateRequest;
import shop.nuribooks.view.review.dto.response.ReviewBookResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.dto.response.ReviewScoreResponse;

public interface ReviewService {
	Page<ReviewMemberResponse> getReviewsByBookId(long bookId, Pageable pageable);
	PagedResponse<ReviewBookResponse> getReviewsByMemberId(long memberId, Pageable pageable);
	void registerReview(ReviewRequest reviewRequest);

	void updateReview(ReviewUpdateRequest reviewRequest, long reviewId);

	ReviewScoreResponse getScoreByBookId(long bookId);
}

package shop.nuribooks.view.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.response.ReviewBookResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;

public interface ReviewService {
	Page<ReviewMemberResponse> getReviewsByBookId(long bookId, Pageable pageable);
	PagedResponse<ReviewBookResponse> getReviewsByMemberId(long memberId, Pageable pageable);
	void registerReview(ReviewRequest reviewRequest);
	void updateReview(ReviewRequest reviewRequest);
}
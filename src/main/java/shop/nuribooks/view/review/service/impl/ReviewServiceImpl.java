package shop.nuribooks.view.review.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.request.ReviewUpdateRequest;
import shop.nuribooks.view.review.dto.response.ReviewBookResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.dto.response.ReviewScoreResponse;
import shop.nuribooks.view.review.feign.ReviewServiceClient;
import shop.nuribooks.view.review.service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewServiceClient reviewServiceClient;

	@Override
	public Page<ReviewMemberResponse> getReviewsByBookId(long bookId, Pageable pageable) {
		return reviewServiceClient.getReviewMember(bookId, pageable.getPageNumber(), pageable.getPageSize());
	}

	@Override
	public PagedResponse<ReviewBookResponse> getReviewsByMemberId(long memberId, Pageable pageable) {
		return null;
	}

	@Override
	public void registerReview(ReviewRequest reviewRequest) {
		reviewServiceClient.registerReview(reviewRequest);
	}

	@Override
	public void updateReview(ReviewUpdateRequest reviewUpdateRequest, long reviewId) {
		reviewServiceClient.updateReview(reviewUpdateRequest, reviewId);
	}

	@Override
	public ReviewScoreResponse getScoreByBookId(long bookId) {
		return reviewServiceClient.getReviewScore(bookId);
	}
}

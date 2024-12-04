package shop.nuribooks.view.review.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.request.ReviewUpdateRequest;
import shop.nuribooks.view.review.dto.response.ReviewBookResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.dto.response.ReviewScoreResponse;

@FeignClient(name = "review", url = "http://localhost:8080")
public interface ReviewServiceClient {
	@GetMapping("/api/books/{bookId}/reviews")
	Page<ReviewMemberResponse> getReviewMember(
		@PathVariable long bookId,
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	@GetMapping("/api/members/{memberId}/reviews")
	PagedResponse<ReviewBookResponse> getReviewBook(
		@PathVariable long memberId,
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	@PostMapping("/api/reviews")
	ReviewMemberResponse registerReview(
		@Valid @RequestBody ReviewRequest reviewRequest
	);

	@PutMapping("/api/reviews/{reviewId}")
	ReviewMemberResponse updateReview(
		@Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest,
		@PathVariable long reviewId
	);

	@GetMapping("/api/books/{bookId}/score")
	ReviewScoreResponse getReviewScore(@PathVariable long bookId);
}

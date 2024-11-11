package shop.nuribooks.view.review.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;

@FeignClient(name = "review", url = "http://localhost:8080")
public interface ReviewServiceClient {
	@GetMapping("/api/books/{bookId}/reviews")
	PagedResponse<ReviewMemberResponse> getReviewMember(@PathVariable long bookId, Pageable pageable);
}

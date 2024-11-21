package shop.nuribooks.view.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/review")
	public ResponseEntity<ResponseMessage> registerReview(@Valid @ModelAttribute ReviewRequest reviewRequest) {
		reviewService.registerReview(reviewRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(new ResponseMessage(HttpStatus.CREATED.value(), "리뷰 생성 성공"));
	}

	@PutMapping("/review/{reviewId}")
	public ResponseEntity<ResponseMessage> updateReview(@Valid @ModelAttribute ReviewRequest reviewRequest,
		@PathVariable("reviewId") long reviewId) {
		reviewService.updateReview(reviewRequest, reviewId);
		return ResponseEntity.status(HttpStatus.OK)
			.body(new ResponseMessage(HttpStatus.OK.value(), "리뷰 수정 성공"));
	}
}

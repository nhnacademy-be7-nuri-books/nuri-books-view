package shop.nuribooks.view.review.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@Value("${success.message-key}")
	private String successMessageKey;

	@Value("${error.message-key}")
	private String errorMessageKey;

	@PostMapping("/review")
	public String registerReview(@Valid @ModelAttribute ReviewRequest reviewRequest,
		RedirectAttributes redirectAttributes) {
		System.out.println(reviewRequest);
		try {
			reviewService.registerReview(reviewRequest);
			redirectAttributes.addFlashAttribute(successMessageKey, "리뷰가 성공적으로 등록되었습니다.");
		} catch (FeignException e) {
			redirectAttributes.addFlashAttribute(errorMessageKey, "리뷰 등록에 실패하였습니다.");
		}
		return "redirect:/view/book/details/" + reviewRequest.bookId();
	}

	@PutMapping("/review/{reviewId}")
	public ResponseEntity<ResponseMessage> updateReview(@Valid @ModelAttribute ReviewRequest reviewRequest,
		@PathVariable("reviewId") long reviewId) {
		reviewService.updateReview(reviewRequest, reviewId);
		return ResponseEntity.status(HttpStatus.OK)
			.body(new ResponseMessage(HttpStatus.OK.value(), "리뷰 수정 성공"));
	}
}

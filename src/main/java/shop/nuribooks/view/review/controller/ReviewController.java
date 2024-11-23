package shop.nuribooks.view.review.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.review.dto.request.ReviewRequest;
import shop.nuribooks.view.review.dto.request.ReviewUpdateRequest;
import shop.nuribooks.view.review.service.ReviewService;

@Slf4j
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
		try {
			reviewService.registerReview(reviewRequest);
			redirectAttributes.addFlashAttribute(successMessageKey, "리뷰가 성공적으로 등록되었습니다.");
		} catch (FeignException e) {
			redirectAttributes.addFlashAttribute(errorMessageKey, e.getMessage());
		}
		return "redirect:/view/book/details/" + reviewRequest.bookId();
	}

	@PutMapping("/review/{reviewId}")
	public String updateReview(@Valid @ModelAttribute ReviewUpdateRequest reviewUpdateRequest,
		@PathVariable("reviewId") long reviewId,
		RedirectAttributes redirectAttributes) {
		try {
			log.error("{}", reviewUpdateRequest);
			reviewService.updateReview(reviewUpdateRequest, reviewId);
			redirectAttributes.addFlashAttribute(successMessageKey, "리뷰가 성공적으로 등록되었습니다.");
		} catch (FeignException e) {
			redirectAttributes.addFlashAttribute(errorMessageKey, e.getMessage());
		}
		return "redirect:/view/book/details/" + reviewUpdateRequest.bookId();
	}
}

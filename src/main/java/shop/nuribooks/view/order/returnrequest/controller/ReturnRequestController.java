package shop.nuribooks.view.order.returnrequest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.dto.response.ReturnRequestResponse;
import shop.nuribooks.view.order.returnrequest.service.ReturnRequestService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReturnRequestController {

	private final ReturnRequestService returnRequestService;

	@GetMapping("/admin/orders/return-requests")
	public String getWrappingPapers(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ReturnRequestResponse> pageResponse = returnRequestService.getReturnRequests(pageable);
		model.addAttribute("pages", pageResponse);
		return "admin/return-request/return-request";
	}

	@PostMapping("/orders/return-requests/{order-id}")
	public ResponseEntity<Void> registerWrappingPaper(
		@PathVariable("order-id") Long orderId,
		@RequestParam("contents") @NotBlank String contents,
		@RequestParam("imageFile") MultipartFile imageFile
	) {
		String imageUrl = returnRequestService.uploadImage(imageFile);
		ReturnRequestRegisterRequest returnRequestRegisterRequest = new ReturnRequestRegisterRequest(contents,
			imageUrl);
		returnRequestService.registerReturnRequest(orderId, returnRequestRegisterRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}

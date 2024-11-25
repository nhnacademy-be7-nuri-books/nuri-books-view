package shop.nuribooks.view.order.returnrequest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.service.ReturnRequestService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders/return-requests")
@Controller
public class ReturnRequestController {

	private final ReturnRequestService returnRequestService;

	@PostMapping
	public ResponseEntity<Void> registerWrappingPaper(
		@RequestParam("order-id") Long orderId,
		@RequestParam("contents") String contents,
		@RequestParam("imageFile") MultipartFile imageFile
	) {
		String imageUrl = returnRequestService.uploadImage(imageFile);
		ReturnRequestRegisterRequest returnRequestRegisterRequest = new ReturnRequestRegisterRequest(contents,
			imageUrl);
		returnRequestService.registerReturnRequest(orderId, returnRequestRegisterRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}

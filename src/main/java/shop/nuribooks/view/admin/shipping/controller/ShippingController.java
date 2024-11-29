package shop.nuribooks.view.admin.shipping.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.shipping.dto.ShippingResponse;
import shop.nuribooks.view.admin.shipping.service.ShippingService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/shipping")
@Controller
public class ShippingController {
	private final ShippingService shippingService;

	@Operation(summary = "배송 목록 조회", description = "배송 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@GetMapping
	public String getShippingResponses(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ShippingResponse> pages = shippingService.getAllShippingInfo(pageable);
		model.addAttribute("pages", pages);
		return "admin/shipping/shipping";
	}

	@Operation(summary = "배송 상세 조회", description = "배송 내역을 상세 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@GetMapping("/{id}")
	public String getShippingDetails(@PathVariable Long id, Model model) {
		model.addAttribute("shipping", shippingService.getShippingResponse(id));
		return "admin/shipping/shipping_details";
	}

	@Operation(summary = "배송 상태 수정", description = "배송 상태를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "배송 상태 수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage> startDelivery(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(shippingService.startDelivery(id));
	}

	@Operation(summary = "배송 완료 처리", description = "배송 정보를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "배송 완료 처리"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PutMapping("/delivery-complete/{id}")
	public ResponseEntity<ResponseMessage> completeDelivery(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(shippingService.completeDelivery(id));
	}
}

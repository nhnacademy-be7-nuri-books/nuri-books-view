package shop.nuribooks.view.admin.shippingpolicy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.admin.shippingpolicy.service.ShippingPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/shipping-policy")
public class ShippingPolicyController {
	private final ShippingPolicyService shippingPolicyService;

	@Operation(summary = "배송 정책 조회", description = "배송 정책을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@GetMapping
	public String getShippingPolicies(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ShippingPolicyResponse> policies = shippingPolicyService.getShippingPolicy(pageable);
		model.addAttribute("pages", policies);
		return "admin/shipping-policy/shipping-policy";
	}

	@Operation(summary = "배송 정책 등록", description = "배송 정책을 등록합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "등록 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PostMapping
	public ResponseEntity<ResponseMessage> registerShippingPolicy(
		@Valid @ModelAttribute ShippingPolicyRequest shippingPolicyRequest) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(shippingPolicyService.registerShippingPolicy(shippingPolicyRequest));
	}

	@Operation(summary = "배송 정책 수정", description = "배송 정책을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PutMapping("/{shipping-policy-id}")
	public ResponseEntity<ResponseMessage> updateShippingPolicy(
		@PathVariable("shipping-policy-id") Long id,
		@Valid @ModelAttribute ShippingPolicyRequest shippingPolicyRequest) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(shippingPolicyService.updateShippingPolicy(id, shippingPolicyRequest));
	}

	@Operation(summary = "배송 정책 만료", description = "배송 정책을 만료합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "만료 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PutMapping("/{shipping-policy-id}/expire")
	public ResponseEntity<ResponseMessage> expireShippingPolicy(@PathVariable("shipping-policy-id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(shippingPolicyService.expireShippingPolicy(id));
	}
}

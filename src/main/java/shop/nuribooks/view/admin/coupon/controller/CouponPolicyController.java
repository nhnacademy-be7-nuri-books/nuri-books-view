package shop.nuribooks.view.admin.coupon.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyResponse;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;
import shop.nuribooks.view.admin.coupon.service.CouponPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon-policy")
public class CouponPolicyController {

	private final CouponPolicyService couponPolicyService;

	@GetMapping
	public String getCouponPolicies(Model model, @PageableDefault Pageable pageable) {
		Page<CouponPolicyResponse> couponPolicies = couponPolicyService.getCouponPolicies(pageable);
		model.addAttribute("pages", couponPolicies);
		model.addAttribute("discountTypes", DiscountType.values());
		return "admin/coupon-policy/coupon-policy";
	}

	@GetMapping("/popup")
	public String showCouponPolicyPopup(Model model, @PageableDefault Pageable pageable) {
		Page<CouponPolicyResponse> couponPolicies = couponPolicyService.getCouponPolicies(pageable);
		model.addAttribute("pages", couponPolicies);
		model.addAttribute("discountTypes", DiscountType.values());
		return "admin/popup/coupon-policy-popup";
	}

	@GetMapping("/detail/{coupon-policy-id}")
	public String getCouponPolicyDetail(@PathVariable(name = "coupon-policy-id") Long id,
		Model model) {
		CouponPolicyResponse couponPolicyResponse = couponPolicyService.getCouponPolicy(id);
		model.addAttribute("couponPolicy", couponPolicyResponse);
		model.addAttribute("discountTypes", DiscountType.values());
		return "admin/coupon-policy/coupon-policy-detail";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerCouponPolicy(
		@Valid @ModelAttribute CouponPolicyRequest couponPolicyRequest) {
		ResponseMessage message = couponPolicyService.registerCouponPolicy(couponPolicyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{coupon-policy-id}")
	public ResponseEntity<ResponseMessage> updateCouponPolicy(@PathVariable("coupon-policy-id") Long id,
		@Valid @ModelAttribute CouponPolicyRequest couponPolicyRequest) {
		ResponseMessage message = couponPolicyService.updateCouponPolicy(id, couponPolicyRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/{coupon-policy-id}")
	public ResponseEntity<ResponseMessage> deleteCouponPolicy(@PathVariable("coupon-policy-id") Long id) {
		ResponseMessage message = couponPolicyService.deleteCouponPolicy(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}

}

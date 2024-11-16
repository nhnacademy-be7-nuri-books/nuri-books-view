package shop.nuribooks.view.admin.coupon.controller;

import java.math.BigDecimal;

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
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.enums.ExpirationType;
import shop.nuribooks.view.admin.coupon.service.CouponService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class CouponController {
	private final CouponService couponService;

	@GetMapping
	public String getPointPolicy(Model model, @PageableDefault Pageable pageable){
		Page<CouponResponse> coupons = this.couponService.getCoupons(pageable);
		model.addAttribute("pages", coupons);
		model.addAttribute("expirationTypes", ExpirationType.values());
		return "admin/coupon/coupon";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerPointPolicy(@Valid @ModelAttribute CouponRequest couponRequest){
		ResponseMessage message = this.couponService.registerCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{coupon-id}")
	public ResponseEntity<ResponseMessage> updatePointPolicy(@PathVariable("coupon-id") Long id, @Valid @ModelAttribute CouponRequest couponRequest){
		ResponseMessage message = this.couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK ).body(message);
	}

	@DeleteMapping("/{coupon-id}")
	public ResponseEntity<ResponseMessage> deleteCoupon(@PathVariable("coupon-id") Long id){
		ResponseMessage message = this.couponService.deleteCoupon(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}
}

package shop.nuribooks.view.member.coupon.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;
import shop.nuribooks.view.member.coupon.service.MemberCouponService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MemberCouponController {
	private final MemberCouponService memberCouponService;

	@GetMapping("/my-coupon/available")
	public String getAvailableCouponList(Model model,
		Pageable pageable) {

		Page<MemberCouponResponse> availableCoupons = memberCouponService.getAvailableCouponsByMemberId(pageable);

		model.addAttribute("pages", availableCoupons);
		model.addAttribute("paginationUrl", "/my-coupon/available");

		return "member/my-coupons";
	}

	@GetMapping("/my-coupon/expired-or-used")
	public String getExpiredOrUsedCouponList(Model model,
		Pageable pageable) {

		Page<MemberCouponResponse> expiredOrUsedCoupons = memberCouponService.getExpiredOrUsedCouponsByMemberId(
			pageable);

		model.addAttribute("pages", expiredOrUsedCoupons);
		model.addAttribute("paginationUrl", "/my-coupon/expired-or-used");

		return "member/my-coupons";
	}

	@PostMapping("/my-coupon/{coupon-id}")
	public ResponseEntity<ResponseMessage> registerCoupon(@PathVariable(name = "coupon-id") Long couponId) {
		memberCouponService.registerMemberCoupon(couponId);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(new ResponseMessage(HttpStatus.CREATED.value(), "쿠폰 발급 성공"));
	}

}

package shop.nuribooks.view.member.coupon.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

		model.addAttribute("coupons", availableCoupons);
		model.addAttribute("paginationUrl", "/my-coupon/available");

		return "member/my-coupons";
	}

	@GetMapping("/my-coupon/expired-or-used")
	public String getExpiredOrUsedCouponList(Model model,
		Pageable pageable) {

		Page<MemberCouponResponse> expiredOrUsedCoupons = memberCouponService.getExpiredOrUsedCouponsByMemberId(
			pageable);

		model.addAttribute("coupons", expiredOrUsedCoupons);
		model.addAttribute("paginationUrl", "/my-coupon/expired-or-used");

		return "member/my-coupons";
	}

}

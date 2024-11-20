package shop.nuribooks.view.member.coupon.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.member.coupon.dto.CouponHistoryPeriodRequest;
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
		@PageableDefault Pageable pageable,
		CouponHistoryPeriodRequest couponHistoryPeriodRequest) {

		Page<MemberCouponResponse> availableCoupons = memberCouponService.getAvailableCouponsByMemberId(
			pageable, couponHistoryPeriodRequest);

		model.addAttribute("coupons", availableCoupons);
		model.addAttribute("period", couponHistoryPeriodRequest);

		return "member/my-coupons";
	}

	@GetMapping("/my-coupon/expired-or-used")
	public String getExpiredOrUsedCouponList(Model model,
		@PageableDefault Pageable pageable,
		CouponHistoryPeriodRequest couponHistoryPeriodRequest) {

		Page<MemberCouponResponse> expiredOrUsedCoupons = memberCouponService.getExpiredOrUsedCouponsByMemberId(
			pageable, couponHistoryPeriodRequest);

		model.addAttribute("coupons", expiredOrUsedCoupons);
		model.addAttribute("period", couponHistoryPeriodRequest);

		return "member/my-coupons";
	}

}

package shop.nuribooks.view.admin.coupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.dto.MemberCouponIssueRequest;
import shop.nuribooks.view.admin.coupon.service.CouponService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member-coupon")
public class AdminMemberCouponController {

	private final CouponService couponService;

	@GetMapping
	public String getCoupons() {
		return "admin/coupon/member-coupon-register";
	}

	@PostMapping
	public String issueMemberCoupon(
		@ModelAttribute MemberCouponIssueRequest memberCouponIssueRequest
	) {
		couponService.issueMemberCoupon(memberCouponIssueRequest);
		return "admin/coupon/member-coupon-register";
	}
}

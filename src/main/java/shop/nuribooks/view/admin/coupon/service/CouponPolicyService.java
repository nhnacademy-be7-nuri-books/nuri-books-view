package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface CouponPolicyService {

	Page<CouponPolicyResponse> getCouponPolicies(Pageable pageable);

	CouponPolicyResponse getCouponPolicy(Long id);

	ResponseMessage registerCouponPolicy(CouponPolicyRequest request);

	ResponseMessage updateCouponPolicy(Long id, CouponPolicyRequest request);

	ResponseMessage deleteCouponPolicy(Long id);

}

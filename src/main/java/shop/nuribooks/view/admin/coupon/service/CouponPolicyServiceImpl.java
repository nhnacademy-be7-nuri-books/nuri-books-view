package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyResponse;
import shop.nuribooks.view.admin.coupon.feign.CouponPolicyServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class CouponPolicyServiceImpl implements CouponPolicyService {
	private final CouponPolicyServiceClient couponPolicyServiceClient;

	@Override
	public Page<CouponPolicyResponse> getCouponPolicies(Pageable pageable) {
		return couponPolicyServiceClient.getCouponPolicies(pageable).getBody();
	}

	@Override
	public CouponPolicyResponse getCouponPolicy(Long id) {
		return couponPolicyServiceClient.getCouponPolicy(id).getBody();
	}

	@Override
	public ResponseMessage registerCouponPolicy(CouponPolicyRequest request) {
		return couponPolicyServiceClient.registerCouponPolicy(request).getBody();
	}

	@Override
	public ResponseMessage updateCouponPolicy(Long id, CouponPolicyRequest request) {
		return couponPolicyServiceClient.updateCouponPolicy(id, request).getBody();
	}

	@Override
	public ResponseMessage deleteCouponPolicy(Long id) {
		return couponPolicyServiceClient.deleteCouponPolicy(id).getBody();
	}
}

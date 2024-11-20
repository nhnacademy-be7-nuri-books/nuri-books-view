package shop.nuribooks.view.admin.shippingpolicy.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.admin.shippingpolicy.feign.ShippingPolicyServiceFeignClient;
import shop.nuribooks.view.admin.shippingpolicy.service.ShippingPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@Service
public class ShippingPolicyServiceImpl implements ShippingPolicyService {
	private final ShippingPolicyServiceFeignClient shippingPolicyServiceFeignClient;

	@Override
	public Page<ShippingPolicyResponse> getShippingPolicy(Pageable pageable) {
		return shippingPolicyServiceFeignClient.getShippingPolicies(pageable).getBody();
	}

	@Override
	public ResponseMessage registerShippingPolicy(ShippingPolicyRequest shippingPolicyRequest) {
		return shippingPolicyServiceFeignClient.registerShippingPolicy(shippingPolicyRequest).getBody();
	}

	@Override
	public ResponseMessage updateShippingPolicy(Long id, ShippingPolicyRequest shippingPolicyRequest) {
		return shippingPolicyServiceFeignClient.updateShippingPolicy(id, shippingPolicyRequest).getBody();
	}

	@Override
	public ResponseMessage expireShippingPolicy(Long id) {
		return shippingPolicyServiceFeignClient.expireShippingPolicy(id).getBody();
	}
}

package shop.nuribooks.view.admin.shippingpolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface ShippingPolicyService {
	Page<ShippingPolicyResponse> getShippingPolicy(Pageable pageable);

	ResponseMessage registerShippingPolicy(ShippingPolicyRequest shippingPolicyRequest);

	ResponseMessage updateShippingPolicy(Long id, ShippingPolicyRequest shippingPolicyRequest);

	ResponseMessage expireShippingPolicy(Long id);
}

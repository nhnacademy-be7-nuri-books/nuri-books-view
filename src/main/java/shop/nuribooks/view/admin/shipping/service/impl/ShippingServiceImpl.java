package shop.nuribooks.view.admin.shipping.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.shipping.dto.ShippingResponse;
import shop.nuribooks.view.admin.shipping.feign.ShippingServiceFeignClient;
import shop.nuribooks.view.admin.shipping.service.ShippingService;

@RequiredArgsConstructor
@Service
public class ShippingServiceImpl implements ShippingService {
	private final ShippingServiceFeignClient shippingServiceFeignClient;

	@Override
	public Page<ShippingResponse> getAllShippingInfo(Pageable pageable) {
		return shippingServiceFeignClient.getShippingResponses(pageable).getBody();
	}

	@Override
	public ShippingResponse getShippingResponse(Long id) {
		return shippingServiceFeignClient.getShippingResponse(id).getBody();
	}
}

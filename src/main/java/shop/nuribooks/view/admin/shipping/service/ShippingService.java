package shop.nuribooks.view.admin.shipping.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.shipping.dto.ShippingResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface ShippingService {
	Page<ShippingResponse> getAllShippingInfo(Pageable pageable);

	ShippingResponse getShippingResponse(Long id);

	ResponseMessage startDelivery(Long id);

	ResponseMessage completeDelivery(Long id);
}

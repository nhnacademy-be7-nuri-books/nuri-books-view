package shop.nuribooks.view.admin.shipping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.nuribooks.view.admin.shipping.dto.ShippingResponse;

@FeignClient(name = "shipping", url = "http://localhost:8080/admin/api/shipping")
public interface ShippingServiceFeignClient {
	@GetMapping
	ResponseEntity<Page<ShippingResponse>> getShippingResponses(Pageable pageable);

	@GetMapping("/{id}")
	ResponseEntity<ShippingResponse> getShippingResponse(@PathVariable Long id);
}

package shop.nuribooks.view.admin.shipping.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.shipping.dto.ShippingResponse;
import shop.nuribooks.view.admin.shipping.service.ShippingService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/shipping")
@Controller
public class ShippingController {
	private final ShippingService shippingService;

	@GetMapping
	public String getShippingResponses(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ShippingResponse> pages = shippingService.getAllShippingInfo(pageable);
		model.addAttribute("pages", pages);
		return "admin/shipping/shipping";
	}

	@GetMapping("/{id}")
	public String getShippingDetails(@PathVariable Long id, Model model) {
		model.addAttribute("shipping", shippingService.getShippingResponse(id));
		return "admin/shipping/shipping_details";
	}
}

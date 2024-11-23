package shop.nuribooks.view.order.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.order.order.dto.request.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.response.OrderTempRegisterResponse;
import shop.nuribooks.view.order.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderRestController {

	private final OrderService orderService;

	/**
	 * 주문 저장 처리
	 *
	 * @param orderTempRegisterRequest
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<OrderTempRegisterResponse> doOrder(
		@RequestBody OrderTempRegisterRequest orderTempRegisterRequest) {

		OrderTempRegisterResponse orderTempRegisterResponse = orderService.saveOrder(orderTempRegisterRequest);
		return ResponseEntity.status(HttpStatus.OK).body(orderTempRegisterResponse);

	}
}

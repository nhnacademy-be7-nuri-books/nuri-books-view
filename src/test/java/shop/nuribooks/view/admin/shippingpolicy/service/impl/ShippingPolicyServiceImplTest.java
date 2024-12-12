package shop.nuribooks.view.admin.shippingpolicy.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.admin.shippingpolicy.feign.ShippingPolicyServiceFeignClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class ShippingPolicyServiceImplTest {

	@InjectMocks
	private ShippingPolicyServiceImpl shippingPolicyService;

	@Mock
	private ShippingPolicyServiceFeignClient shippingPolicyServiceFeignClient;

	private ShippingPolicyRequest shippingPolicyRequest;
	private ShippingPolicyResponse shippingPolicyResponse;
	private ResponseMessage responseMessage;

	@BeforeEach
	void setUp() {
		shippingPolicyRequest = new ShippingPolicyRequest(5000, LocalDateTime.of(2024, 12, 31, 23, 59, 59, 0),
			BigDecimal.valueOf(10000));
		shippingPolicyResponse = new ShippingPolicyResponse(1L, 5000, LocalDateTime.of(2024, 12, 31, 23, 59, 59, 0),
			BigDecimal.valueOf(10000));
		responseMessage = new ResponseMessage(200, "Success");
	}

	@Test
	void testGetShippingPolicy() {
		Page<ShippingPolicyResponse> page = new PageImpl<>(Collections.singletonList(shippingPolicyResponse),
			PageRequest.of(0, 5), 1);

		when(shippingPolicyServiceFeignClient.getShippingPolicies(any())).thenReturn(ResponseEntity.ok(page));

		Page<ShippingPolicyResponse> result = shippingPolicyService.getShippingPolicy(PageRequest.of(0, 5));

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(shippingPolicyResponse, result.getContent().get(0));

		verify(shippingPolicyServiceFeignClient).getShippingPolicies(any());
	}

	@Test
	void testRegisterShippingPolicy() {
		when(shippingPolicyServiceFeignClient.registerShippingPolicy(any(ShippingPolicyRequest.class))).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = shippingPolicyService.registerShippingPolicy(shippingPolicyRequest);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(shippingPolicyServiceFeignClient).registerShippingPolicy(any(ShippingPolicyRequest.class));
	}

	@Test
	void testUpdateShippingPolicy() {
		when(shippingPolicyServiceFeignClient.updateShippingPolicy(anyLong(),
			any(ShippingPolicyRequest.class))).thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = shippingPolicyService.updateShippingPolicy(1L, shippingPolicyRequest);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(shippingPolicyServiceFeignClient).updateShippingPolicy(anyLong(), any(ShippingPolicyRequest.class));
	}

	@Test
	void testExpireShippingPolicy() {
		when(shippingPolicyServiceFeignClient.expireShippingPolicy(anyLong())).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = shippingPolicyService.expireShippingPolicy(1L);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(shippingPolicyServiceFeignClient).expireShippingPolicy(anyLong());
	}
}

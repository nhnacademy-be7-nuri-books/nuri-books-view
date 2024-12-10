package shop.nuribooks.view.admin.shippingpolicy.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.admin.shippingpolicy.service.ShippingPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class ShippingPolicyControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ShippingPolicyController shippingPolicyController;

	@Mock
	private ShippingPolicyService shippingPolicyService;

	private ShippingPolicyRequest shippingPolicyRequest;
	private ShippingPolicyResponse shippingPolicyResponse;
	private ResponseMessage responseMessage;

	@BeforeEach
	void setUp() {
		shippingPolicyRequest = new ShippingPolicyRequest(
			5000,
			LocalDateTime.of(2024, 12, 31, 23, 59, 59, 0),
			BigDecimal.valueOf(10000)
		);

		shippingPolicyResponse = new ShippingPolicyResponse(
			1L,
			5000,
			LocalDateTime.of(2024, 12, 31, 23, 59, 59, 0),
			BigDecimal.valueOf(10000)
		);

		responseMessage = new ResponseMessage(200, "Success");

		mockMvc = MockMvcBuilders.standaloneSetup(shippingPolicyController).build();
	}

	@Test
	void testRegisterShippingPolicy() throws Exception {
		when(shippingPolicyService.registerShippingPolicy(any(ShippingPolicyRequest.class)))
			.thenReturn(responseMessage);

		mockMvc.perform(post("/admin/shipping-policy")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("shippingFee", "5000")
				.param("expiration", "2024-12-31T23:59:59")
				.param("minimumOrderPrice", "10000"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.statusCode", is(200)))
			.andExpect(jsonPath("$.message", is("Success")));

		verify(shippingPolicyService).registerShippingPolicy(any(ShippingPolicyRequest.class));
	}

	@Test
	void testUpdateShippingPolicy() throws Exception {
		when(shippingPolicyService.updateShippingPolicy(eq(1L), any(ShippingPolicyRequest.class)))
			.thenReturn(responseMessage);

		mockMvc.perform(put("/admin/shipping-policy/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("shippingFee", "5000")
				.param("expiration", "2024-12-31T23:59:59")
				.param("minimumOrderPrice", "10000"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.statusCode", is(200)))
			.andExpect(jsonPath("$.message", is("Success")));

		verify(shippingPolicyService).updateShippingPolicy(eq(1L), any(ShippingPolicyRequest.class));
	}

	@Test
	void testExpireShippingPolicy() throws Exception {
		when(shippingPolicyService.expireShippingPolicy(1L))
			.thenReturn(responseMessage);

		mockMvc.perform(put("/admin/shipping-policy/1/expire"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.statusCode", is(200)))
			.andExpect(jsonPath("$.message", is("Success")));

		verify(shippingPolicyService).expireShippingPolicy(1L);
	}
}

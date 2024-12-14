package shop.nuribooks.view.admin.coupon.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;
import shop.nuribooks.view.admin.coupon.service.CouponPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class CouponPolicyControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private CouponPolicyController couponPolicyController;

	@Mock
	private CouponPolicyService couponPolicyService;

	private ResponseMessage responseMessage;
	private ResponseMessage deletedMessage;
	private CouponPolicyRequest couponPolicyRequest;

	@BeforeEach
	void setUp() {
		responseMessage = new ResponseMessage(201, "Success");
		deletedMessage = new ResponseMessage(204, "Success");
		couponPolicyRequest = new CouponPolicyRequest("Test Policy", DiscountType.RATED, BigDecimal.valueOf(5000),
			BigDecimal.valueOf(2000), 10);

		mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyController).build();
	}

	@Test
	void testRegisterCouponPolicy() throws Exception {
		when(couponPolicyService.registerCouponPolicy(any())).thenReturn(responseMessage);

		mockMvc.perform(post("/admin/coupon-policy").flashAttr("couponPolicyRequest", couponPolicyRequest))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.statusCode").value(201))
			.andExpect(jsonPath("$.message").value("Success"));

		verify(couponPolicyService).registerCouponPolicy(any());
	}

	@Test
	void testUpdateCouponPolicy() throws Exception {
		when(couponPolicyService.updateCouponPolicy(anyLong(), any())).thenReturn(responseMessage);

		mockMvc.perform(
				put("/admin/coupon-policy/{coupon-policy-id}", 1L).flashAttr("couponPolicyRequest", couponPolicyRequest))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.statusCode").value(201))
			.andExpect(jsonPath("$.message").value("Success"));

		verify(couponPolicyService).updateCouponPolicy(eq(1L), any());
	}

	@Test
	void testDeleteCouponPolicy() throws Exception {
		when(couponPolicyService.deleteCouponPolicy(anyLong())).thenReturn(deletedMessage);

		mockMvc.perform(delete("/admin/coupon-policy/{coupon-policy-id}", 1L))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.statusCode").value(204))
			.andExpect(jsonPath("$.message").value("Success"));

		verify(couponPolicyService).deleteCouponPolicy(1L);
	}
}

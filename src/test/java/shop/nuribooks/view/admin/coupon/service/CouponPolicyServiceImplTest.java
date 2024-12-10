package shop.nuribooks.view.admin.coupon.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
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

import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyResponse;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;
import shop.nuribooks.view.admin.coupon.feign.CouponPolicyServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class CouponPolicyServiceImplTest {

	@InjectMocks
	private CouponPolicyServiceImpl couponPolicyService;

	@Mock
	private CouponPolicyServiceClient couponPolicyServiceClient;

	private CouponPolicyResponse couponPolicyResponse;
	private CouponPolicyRequest couponPolicyRequest;
	private ResponseMessage responseMessage;

	@BeforeEach
	void setUp() {
		couponPolicyResponse = new CouponPolicyResponse(
			1L,
			"Test Policy",
			DiscountType.RATED,
			BigDecimal.valueOf(5000),
			BigDecimal.valueOf(2000),
			10
		);

		couponPolicyRequest = new CouponPolicyRequest(
			"Test Policy",
			DiscountType.RATED,
			BigDecimal.valueOf(5000),
			BigDecimal.valueOf(2000),
			10
		);

		responseMessage = new ResponseMessage(200, "Success");
	}

	@Test
	void testGetCouponPolicies() {
		Page<CouponPolicyResponse> page = new PageImpl<>(Collections.singletonList(couponPolicyResponse),
			PageRequest.of(0, 5), 1);
		when(couponPolicyServiceClient.getCouponPolicies(any())).thenReturn(ResponseEntity.ok(page));

		Page<CouponPolicyResponse> result = couponPolicyService.getCouponPolicies(PageRequest.of(0, 5));

		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getContent()).containsExactly(couponPolicyResponse);

		verify(couponPolicyServiceClient).getCouponPolicies(any());
	}

	@Test
	void testGetCouponPolicy() {
		when(couponPolicyServiceClient.getCouponPolicy(anyLong())).thenReturn(ResponseEntity.ok(couponPolicyResponse));

		CouponPolicyResponse result = couponPolicyService.getCouponPolicy(1L);

		assertThat(result).isEqualTo(couponPolicyResponse);
		verify(couponPolicyServiceClient).getCouponPolicy(1L);
	}

	@Test
	void testRegisterCouponPolicy() {
		when(couponPolicyServiceClient.registerCouponPolicy(any())).thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = couponPolicyService.registerCouponPolicy(couponPolicyRequest);

		assertThat(result.statusCode()).isEqualTo(200);
		assertThat(result.message()).isEqualTo("Success");
		verify(couponPolicyServiceClient).registerCouponPolicy(any());
	}

	@Test
	void testUpdateCouponPolicy() {
		when(couponPolicyServiceClient.updateCouponPolicy(anyLong(), any())).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = couponPolicyService.updateCouponPolicy(1L, couponPolicyRequest);

		assertThat(result.statusCode()).isEqualTo(200);
		assertThat(result.message()).isEqualTo("Success");
		verify(couponPolicyServiceClient).updateCouponPolicy(eq(1L), any());
	}

	@Test
	void testDeleteCouponPolicy() {
		when(couponPolicyServiceClient.deleteCouponPolicy(anyLong())).thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = couponPolicyService.deleteCouponPolicy(1L);

		assertThat(result.statusCode()).isEqualTo(200);
		assertThat(result.message()).isEqualTo("Success");
		verify(couponPolicyServiceClient).deleteCouponPolicy(1L);
	}
}

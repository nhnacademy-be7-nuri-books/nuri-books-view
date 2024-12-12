package shop.nuribooks.view.admin.coupon.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.admin.coupon.enums.ExpirationType;
import shop.nuribooks.view.admin.coupon.enums.IssuanceType;

public record CouponRequest(
	@NotNull(message = "이름은 필수입니다.")
	@Size(min = 2, max = 50)
	String name,

	@NotNull(message = "쿠폰유형은 필수입니다.") //전체 적용, 도서 적용, 카테고리 적용
	CouponType couponType,

	@NotNull(message = "쿠폰 할인 유형은 필수입니다.")
	Long couponPolicyId,

	@NotNull(message = "만료유형은 필수입니다.") //기간 쿠폰, 만료일 쿠폰
	ExpirationType expirationType,

	LocalDate expiredAt,

	@Min(value = 1, message = "할인 기간은 1일 이상이어야 합니다.")
	Integer period,

	@NotNull(message = "수량 제한 여부는 필수 입니다.")
	IssuanceType issuanceType,

	Integer quantity,

	Long itemId
) {
}

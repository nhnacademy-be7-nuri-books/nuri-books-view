package shop.nuribooks.view.admin.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;
import shop.nuribooks.view.admin.coupon.enums.ExpirationType;
import shop.nuribooks.view.admin.coupon.enums.IssuanceType;

public record CouponResponse(
	Long id,
	String name,
	CouponType couponType,
	DiscountType discountType,
	int discount,
	BigDecimal minimumOrderPrice,
	BigDecimal maximumDiscountPrice,
	ExpirationType expirationType,
	LocalDate expiredAt,
	int period,
	IssuanceType issuanceType,
	int quantity,
	Long itemId,
	String itemName,
	LocalDate createdAt,
	LocalDateTime deletedAt
) {
}

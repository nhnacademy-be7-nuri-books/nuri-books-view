package shop.nuribooks.view.admin.coupon.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryCouponRequest(@NotNull Long categoryId) {
}

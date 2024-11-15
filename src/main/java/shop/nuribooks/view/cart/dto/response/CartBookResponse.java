package shop.nuribooks.view.cart.dto.response;

import java.math.BigDecimal;

public record CartBookResponse(Long bookId,
							   String title,
							   BigDecimal price,
							   int discountRate,
							   BigDecimal salePrice,
							   BigDecimal totalPrice,
							   String thumbnailImageUrl,
							   int quantity) {
}

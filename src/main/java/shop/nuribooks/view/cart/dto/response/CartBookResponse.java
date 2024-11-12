package shop.nuribooks.view.cart.dto.response;

import java.math.BigDecimal;

public record CartBookResponse(String title,
							   BigDecimal price,
							   int discountRate,
							   String thumbnailImageUrl) {
}

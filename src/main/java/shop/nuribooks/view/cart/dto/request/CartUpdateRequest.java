package shop.nuribooks.view.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartUpdateRequest(
        @NotNull Long bookId,
		@Min(1) int quantity) {
}

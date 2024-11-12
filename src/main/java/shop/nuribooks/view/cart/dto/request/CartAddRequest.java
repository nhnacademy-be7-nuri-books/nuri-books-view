package shop.nuribooks.view.cart.dto.request;

import jakarta.validation.constraints.Min;

public record CartAddRequest(
        String cartId,
        Long bookId,
        int quantity) {
}

package shop.nuribooks.view.cart.dto.response;

import shop.nuribooks.view.book.dto.BookResponse;

public record CartResponse(CartBookResponse cartBookResponse, int quantity) {
}

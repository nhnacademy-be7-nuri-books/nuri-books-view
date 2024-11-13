package shop.nuribooks.view.cart.dto.request;

public record CartAddRequestToServer(
	String cartId,
	Long bookId,
	int quantity) {
}

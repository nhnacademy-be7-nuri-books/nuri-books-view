package shop.nuribooks.view.cart.dto.request;

public record CartRequestToServer(
	String cartId,
	Long bookId,
	int quantity) {
}

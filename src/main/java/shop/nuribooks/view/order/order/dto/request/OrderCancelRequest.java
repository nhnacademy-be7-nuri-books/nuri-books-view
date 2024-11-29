package shop.nuribooks.view.order.order.dto.request;

public record OrderCancelRequest(
	String reason,
	Long customerId) {
}

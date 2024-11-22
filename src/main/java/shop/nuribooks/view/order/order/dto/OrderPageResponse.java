package shop.nuribooks.view.order.order.dto;

import java.util.List;

public record OrderPageResponse(
	List<OrderListResponse> orders,
	Long totalCount
) {
}

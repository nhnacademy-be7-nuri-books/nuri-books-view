package shop.nuribooks.view.order.returnrequest.dto.response;

import lombok.Builder;

@Builder
public record ReturnRequestResponse(
	Long id,
	String contents,
	String imageUrl
) {
}

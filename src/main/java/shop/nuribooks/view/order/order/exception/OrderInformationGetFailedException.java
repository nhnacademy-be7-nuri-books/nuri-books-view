package shop.nuribooks.view.order.order.exception;

import shop.nuribooks.view.exception.BadRequestException;

public class OrderInformationGetFailedException extends BadRequestException {
	public OrderInformationGetFailedException(String message) {
		super(String.format("주문 폼 정보 불러오기 실패 : %s", message));
	}
}

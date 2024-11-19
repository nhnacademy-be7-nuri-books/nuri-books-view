package shop.nuribooks.view.payment.service;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.payment.dto.PaymentSuccessRequest;

public interface PaymentService {
	ResponseMessage paymentSuccessRequest(PaymentSuccessRequest paymentSuccessRequest);
}

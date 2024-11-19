package shop.nuribooks.view.payment.service;

import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.payment.dto.PaymentSuccessRequest;
import shop.nuribooks.view.payment.feign.PaymentServiceClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	private final PaymentServiceClient paymentServiceClient;

	/**
	 * 결제 완료 정보 저장처리
	 *
	 * @param paymentSuccessRequest 토스 페이먼츠 결제 완료 정보
	 * @return 결과 메시지
	 */
	@Override
	public ResponseMessage paymentSuccessRequest(PaymentSuccessRequest paymentSuccessRequest) {
		ResponseMessage responseMessage = null;
		try {
			responseMessage = paymentServiceClient.donePayment(paymentSuccessRequest).getBody();
		} catch (FeignException e) {
			responseMessage = ResponseMessage.builder().statusCode(e.status()).message(e.getMessage()).build();
		}

		return responseMessage;
	}
}

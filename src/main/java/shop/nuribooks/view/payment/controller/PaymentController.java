package shop.nuribooks.view.payment.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.payment.dto.PaymentRequest;
import shop.nuribooks.view.payment.dto.PaymentSuccessRequest;
import shop.nuribooks.view.payment.service.PaymentService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payments")
public class PaymentController {

	private final PaymentService paymentService;

	/**
	 * 인증성공처리
	 * @param request HttpServletRequest
	 * @param model Model
	 * @return 성공 페이지
	 * @throws Exception 토스 페이먼츠 에러
	 */
	@GetMapping(value = "/success")
	public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
		return "payment/success";
	}

	/**
	 * 인증실패처리
	 * @param request HttpServletRequest
	 * @param model Model
	 * @return 실패 페이지
	 * @throws Exception 토스 페이먼츠 에러
	 */
	@GetMapping(value = "/fail")
	public String failPayment(HttpServletRequest request, Model model) throws Exception {

		String failCode = request.getParameter("code");
		String failMessage = request.getParameter("message");

		model.addAttribute("code", failCode);
		model.addAttribute("message", failMessage);

		return "payment/fail";
	}

	/**
	 * 결제 확인
	 *
	 * @param jsonBody 토스페이먼츠로부터 받은 요청
	 * @return 토스페이먼츠로부터 받은 응답
	 * @throws Exception 예외 처리
	 */
	@RequestMapping(value = "/confirm")
	public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

		JSONParser parser = new JSONParser();
		String orderId;
		String amount;
		String paymentKey;
		try {
			// 클라이언트에서 받은 JSON 요청 바디
			JSONObject requestData = (JSONObject)parser.parse(jsonBody);
			paymentKey = (String)requestData.get("paymentKey");
			orderId = (String)requestData.get("orderId");
			amount = (String)requestData.get("amount");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		JSONObject paymentInformation = new JSONObject();
		paymentInformation.put("orderId", orderId);
		paymentInformation.put("amount", amount);
		paymentInformation.put("paymentKey", paymentKey);

		PaymentRequest paymentRequest = new PaymentRequest(
			String.valueOf(paymentInformation.get("orderId")),
			String.valueOf(paymentInformation.get("amount")),
			String.valueOf(paymentInformation.get("paymentKey"))
		);

		// 주문된 정보의 가격 변조 확인
		ResponseMessage verifyMessage = paymentService.verifyOrderInformation(paymentRequest);
		if (verifyMessage.statusCode() != 200) {
			log.error("결제 금액 검증 실패로 결제 실패");

			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("statusCode", verifyMessage.statusCode());
			jsonResponse.put("message", verifyMessage.message());

			return ResponseEntity.status(verifyMessage.statusCode()).body(jsonResponse);
		}

		String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";

		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
		String authorizations = "Basic " + new String(encodedBytes);

		// 결제 승인 API를 호출
		URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("Authorization", authorizations);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(paymentInformation.toString().getBytes("UTF-8"));

		int code = connection.getResponseCode();

		boolean isSuccess = code == 200;

		InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

		Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
		JSONObject jsonObject = (JSONObject)parser.parse(reader);
		responseStream.close();

		if (isSuccess) {
			// 주문 상태값 변경
			PaymentSuccessRequest paymentSuccessRequest = PaymentSuccessRequest.builder()
				.status((String)jsonObject.get("status"))
				.orderId((String)jsonObject.get("orderId"))
				.paymentKey((String)jsonObject.get("paymentKey"))
				.requestedAt(OffsetDateTime.parse((String)jsonObject.get("requestedAt")).toLocalDateTime())
				.method((String)jsonObject.get("method"))
				.approvedAt(OffsetDateTime.parse((String)jsonObject.get("approvedAt")).toLocalDateTime())
				.totalAmount((Long)jsonObject.get("totalAmount"))
				.build();

			ResponseMessage responseMessage = paymentService.paymentSuccessRequest(
				paymentSuccessRequest);

			if (responseMessage.statusCode() != 201) {
				log.error("결제 저장 실패");
				code = responseMessage.statusCode();
			}
		}

		return ResponseEntity.status(code).body(jsonObject);

	}
}

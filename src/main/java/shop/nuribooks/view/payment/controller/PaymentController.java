package shop.nuribooks.view.payment.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.payment.dto.PaymentRequest;

@Controller
@Slf4j
@RequestMapping("/payments")
public class PaymentController {

	/**
	 * 인증성공처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/success")
	public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
		// todo : 결제 인증 성공 처리 - AMOUNT 가 서버에서 저장된 AMOUNT와 같은 지 확인 하기
		return "payment/success";
	}

	/**
	 * 인증실패처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/fail")
	public String failPayment(HttpServletRequest request, Model model) throws Exception {

		String failCode = request.getParameter("code");
		String failMessage = request.getParameter("message");

		model.addAttribute("code", failCode);
		model.addAttribute("message", failMessage);

		return "payment/fail";
	}

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

		// todo : 주문된 정보의 가격 변조 확인
		PaymentRequest paymentRequest = new ObjectMapper().readValue(jsonBody, PaymentRequest.class);

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
			// todo : 주문 상태값 변경,
		}

		return ResponseEntity.status(code).body(jsonObject);

	}
}

package shop.nuribooks.view.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.exception.CustomJsonProcessingException;
import shop.nuribooks.view.exception.DefaultServerError;

/**
 * 예외 처리 유틸리티 클래스
 * feign 클라이언트에서 발생하는 예외를 처리
 * 주어진 feignException 을 분석하고 적절한 메시지를 반환
 *
 * @author nuri
 */
@Slf4j
public class ExceptionUtil {

	/**
	 * FeignException 을 처리하여 사용자 친화적인 메시지를 반환
	 *
	 * @param ex 처리할 FeignException
	 * @return 상태 코드와 예외 메시지 반환
	 */
	public static String handleFeignException(FeignException ex) {

		String methodKey = ex.request().httpMethod().toString() + "," + ex.request().url();
		log.info("{}", methodKey);

		String responseBody = ex.contentUTF8();

		String message = ex.getMessage();
		JsonNode jsonNode = parseResponseBody(responseBody);

		if (jsonNode.has("message")) {
			message = jsonNode.get("message").asText();
		} else if (jsonNode.has("error")) {
			message = jsonNode.get("error").asText();
		}

		return handleStatusCode(ex.status(), message);

	}

	/**
	 * 응답 본문을 JSON 으로 파싱
	 *
	 * @param responseBody JSON 문자열
	 * @return 파싱된 JsonNode 객체
	 * @throws CustomJsonProcessingException JSON 파싱에 실패할 경우
	 */
	private static JsonNode parseResponseBody(String responseBody) {
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(responseBody);
		} catch (JsonProcessingException e) {
			throw new CustomJsonProcessingException("서버 응답 메시지가 잘못되었습니다. (json Processing 실패)");
		}

		return jsonNode;
	}

	/**
	 * 상태 코드에 따라 적절한 메시지를 반환하거나 예외 처리
	 *
	 * @param status 실제 HTTP 상태 코드
	 * @param message 응답 JSON 에서 추출한 메시지
	 * @return 상태 코드에 따른 처리 결과 메시지
	 * @throws DefaultServerError 서버 오류가 발생한 경우
	 */
	private static String handleStatusCode(int status, String message) {
		switch (status) {
			case 400, 401, 403, 409 -> {
				return message;
			}
			default -> throw new DefaultServerError(status, message);
		}
	}
}

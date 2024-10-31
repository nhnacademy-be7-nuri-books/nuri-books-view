package shop.nuribooks.view.common.dto;

/**
 * 오류 응답 데이터 전송 객체.
 *
 * @param statusCode 오류 상태 코드
 * @param message 오류 메시지
 * @param details 오류에 대한 추가 정보
 */
public record ErrorResponse(int statusCode, String message, String details) {
}

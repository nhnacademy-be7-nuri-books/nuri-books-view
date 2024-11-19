package shop.nuribooks.view.common.dto;

import lombok.Builder;

/**
 * 응답 메시지 데이터 전송 객체.
 *
 * @param statusCode 응답 코드 (예: 성공, 오류 코드 등)
 * @param message 응답 메시지 (예: 성공 또는 오류에 대한 설명)
 */
@Builder
public record ResponseMessage(int statusCode, String message) {
}

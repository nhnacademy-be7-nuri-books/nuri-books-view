package shop.nuribooks.view.member.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 회원 등록 응답 데이터 전송 객체.
 *
 * <p>
 * 회원 등록 성공 시 반환되는 정보
 * </p>
 *
 * @param name 회원의 이름
 * @param userId 회원의 사용자 ID
 * @param phoneNumber 회원의 전화번호
 * @param email 회원의 이메일 주소
 * @param birthday 회원의 생일 (yyyy-MM-dd 형식)
 */
public record MemberRegisterResponse(
	String name,
	String userId,
	String phoneNumber,
	String email,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDate birthday
) {
}


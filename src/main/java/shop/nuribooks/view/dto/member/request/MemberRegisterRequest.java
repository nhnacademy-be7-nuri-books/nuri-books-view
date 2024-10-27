package shop.nuribooks.view.dto.member.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 회원 등록 요청 데이터 전송 객체.
 *
 * <p>
 * 회원가입 시 클라이언트에서 입력한 정보를 포함,
 * 입력값의 유효성을 검사
 * </p>
 *
 * @param name 회원의 이름
 * @param userId 회원의 사용자 ID
 * @param password 회원의 비밀번호
 * @param phoneNumber 회원의 휴대폰 번호
 * @param email 회원의 이메일 주소
 * @param birthday 회원의 생일 (yyyy-MM-dd 형식)
 */
public record MemberRegisterRequest(
	@NotBlank(message = "사용자 이름은 필수 입력입니다.")
	@Size(min = 2, max = 30, message = "사용자 이름은 2자 이상 30자 이하로 입력해야 합니다.")
	String name,

	@NotBlank(message = "사용자 아이디는 필수 입력입니다.")
	@Size(min = 8, max = 20, message = "사용자 아이디는 8자 이상 20자 이하로 입력해야 합니다.")
	String userId,

	@NotBlank(message = "사용자 비밀번호는 필수 입력입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
	String password,

	@NotBlank(message = "휴대폰 번호는 필수 입력입니다.")
	@Pattern(regexp = "^\\d{10,13}$", message = "휴대폰 번호는 10자에서 13자 사이의 숫자로 입력해야 합니다.")
	String phoneNumber,

	@NotBlank(message = "이메일은 필수 입력입니다.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	@Size(min = 5, max = 50, message = "이메일은 5자 이상 50자 이하로 입력해야 합니다.")
	String email,

	@NotNull(message = "생일은 반드시 입력해야 합니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDate birthday
) {
}

package shop.nuribooks.view.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 로드인 요청 데이터 객체
 *
 * @param username 사용자 ID
 * @param password 비밀번호
 */
public record LoginRequest(

	@NotBlank(message = "사용자 이름은 필수 입력입니다.")
	@Size(min = 8, max = 20, message = "사용자 아이디는 8자 이상 20자 이하로 입력해야 합니다.")
	String username,

	@NotBlank(message = "비밀번호는 필수 입력입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
	String password
) {
}

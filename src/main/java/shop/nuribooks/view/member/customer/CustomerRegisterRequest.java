package shop.nuribooks.view.member.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CustomerRegisterRequest(

	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	String name,

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	String password,

	@NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
	String phoneNumber,

	@NotBlank(message = "이메일은 반드시 입력해야 합니다.")
	@Email(message = "유효한 이메일 형식으로 입력해야 합니다.")
	String email
) {
}

package shop.nuribooks.view.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record MemberPasswordUpdateRequest(

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$",
		message = "비밀번호는 8자 이상 20자 이하, 영문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.")
	String password
) {
}

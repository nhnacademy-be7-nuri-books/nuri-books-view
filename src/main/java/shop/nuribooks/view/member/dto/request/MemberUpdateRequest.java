package shop.nuribooks.view.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberUpdateRequest (

	String username,

	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	String name,

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	String password
) {}


package shop.nuribooks.view.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberPasswordUpdateRequest(

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	String password
) {}


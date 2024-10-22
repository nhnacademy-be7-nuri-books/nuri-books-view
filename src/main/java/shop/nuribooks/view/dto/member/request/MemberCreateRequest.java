package shop.nuribooks.view.dto.member.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberCreateRequest {

	@NotBlank(message = "사용자 이름은 필수 입력입니다.")
	private String name;

	@NotBlank(message = "사용자 아이디은 필수 입력입니다.")
	private String userId;

	@NotBlank(message = "사용자 비밀번호은 필수 입력입니다.")
	private String password;

	@NotBlank(message = "휴대폰 번호는 필수 입력입니다.")
	private String phoneNumber;

	@NotBlank(message = "이메일은 필수 입력입니다.")
	private String email;

	private LocalDate birthday;
}


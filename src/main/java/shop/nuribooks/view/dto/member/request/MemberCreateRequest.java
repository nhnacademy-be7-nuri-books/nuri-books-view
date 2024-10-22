package shop.nuribooks.view.dto.member.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberCreateRequest {

	@NotBlank(message = "사용자 이름은 필수 입력입니다.")
	@Size(min = 2, max = 30, message = "사용자 이름은 2자 이상 30자 이하로 입력해야 합니다.")
	private String name;

	@NotBlank(message = "사용자 아이디은 필수 입력입니다.")
	@Size(min = 4, max = 20, message = "사용자 아이디는 4자 이상 20자 이하로 입력해야 합니다.")
	private String userId;

	@NotBlank(message = "사용자 비밀번호은 필수 입력입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
	private String password;

	@NotBlank(message = "휴대폰 번호는 필수 입력입니다.")
	@Pattern(regexp = "^\\d{10,13}$", message = "휴대폰 번호는 10자에서 13자 사이의 숫자로 입력해야 합니다.")
	private String phoneNumber;

	@NotBlank(message = "이메일은 필수 입력입니다.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	@Size(min = 5, max = 50, message = "이메일은 5자 이상 50자 이하로 입력해야 합니다.")
	private String email;

	@NotNull(message = "생일은 반드시 입력해야 합니다.")
	private LocalDate birthday;

}

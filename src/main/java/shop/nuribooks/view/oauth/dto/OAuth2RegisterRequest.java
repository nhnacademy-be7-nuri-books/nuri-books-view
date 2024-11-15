package shop.nuribooks.view.oauth.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.nuribooks.view.member.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.member.entity.GenderType;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2RegisterRequest {
	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	private String name;

	@NotNull(message = "성별은 반드시 입력해야 합니다.")
	private GenderType gender;

	@NotBlank(message = "아이디는 반드시 입력해야 합니다.")
	private String username;

	private String password;

	@NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
	private String phoneNumber;

	@NotBlank(message = "이메일은 반드시 입력해야 합니다.")
	@Email(message = "유효한 이메일 형식으로 입력해야 합니다.")
	private String email;

	@NotNull(message = "생일은 반드시 입력해야 합니다.")
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate birthday;

	public MemberRegisterRequest toRecord() {
		return new MemberRegisterRequest(name, gender, username, password, phoneNumber, email, birthday);
	}
}

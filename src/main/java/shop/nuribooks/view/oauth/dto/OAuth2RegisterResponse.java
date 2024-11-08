package shop.nuribooks.view.oauth.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shop.nuribooks.view.member.entity.GenderType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2RegisterResponse {
	private String name;
	private GenderType gender;
	private String username;
	private String phoneNumber;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate birthday;
}

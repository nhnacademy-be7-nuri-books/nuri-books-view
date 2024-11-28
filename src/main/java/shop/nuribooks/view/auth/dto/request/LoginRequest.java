package shop.nuribooks.view.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 로드인 요청 데이터 객체
 *
 * @param username 사용자 ID
 * @param password 비밀번호
 */
public record LoginRequest(

        @NotBlank(message = "아이디는 반드시 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,20}$",
                message = "아이디는 8자 이상 20자 이하, 영어 소문자와 숫자가 각각 1개 이상 포함되어야 합니다.")
        String username,

        @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$",
                message = "비밀번호는 8자 이상 20자 이하, 영문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.")
        String password
) {
}

package shop.nuribooks.view.member.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import shop.nuribooks.view.member.member.entity.GenderType;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Builder
public record MemberRegisterRequest(

        @NotBlank(message = "이름은 반드시 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣]{2,30}$",
                message = "사용자 이름은 영문자와 완성형 한글만 포함하여 2자 이상 30자 이하로 입력해야 합니다.")
        String name,

        @NotNull(message = "성별은 반드시 입력해야 합니다.")
        GenderType gender,

        @NotBlank(message = "아이디는 반드시 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,20}$",
                message = "아이디는 8자 이상 20자 이하, 영어 소문자와 숫자가 각각 1개 이상 포함되어야 합니다.")
        String username,

        @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$",
                message = "비밀번호는 8자 이상 20자 이하, 영문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.")
        String password,

        @NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
        @Pattern(regexp = "^010\\d{8}$",
                message = "전화번호는 '-' 없이 '010'으로 시작하는 11자리의 숫자로 입력해야 합니다.")
        String phoneNumber,

        @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
                message = "유효한 이메일 형식으로 입력해야 합니다.")
        @Size(max = 30, message = "이메일은 30자 이내로 입력해야 합니다.")
        String email,

        @NotNull(message = "생일은 반드시 입력해야 합니다.")
        @PastOrPresent(message = "생일은 현재 날짜를 앞설 수 없습니다.")
        @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthday
) {
}

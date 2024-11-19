package shop.nuribooks.view.admin.contributor.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ContributorRequest(
	@NotBlank(message = "기여자 이름은 필수입니다.")
	@Length(min = 1, max = 50) String name
) {
}

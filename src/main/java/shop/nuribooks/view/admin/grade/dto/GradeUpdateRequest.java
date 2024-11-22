package shop.nuribooks.view.admin.grade.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GradeUpdateRequest(

	@NotBlank(message = "등급명은 반드시 입력해야 합니다.")
	String name,

	@NotNull(message = "포인트 적립률은 반드시 입력해야 합니다.")
	@Min(value = 0, message = "포인트 적립률은 0 이상의 정수입니다.")
	@Max(value = 100, message = "포인트 적립률은 100을 초과할 수 없습니다.")
	Integer pointRate,

	@NotNull(message = "등급의 승급 조건 금액은 반드시 입력해야 합니다.")
	@DecimalMin(value = "0", message = "등급의 승급 조건 금액은 0원 이상이어야 합니다.")
	@DecimalMax(value = "100000000", message = "등급의 승급 조건 금액은 1억원을 초과할 수 없습니다.")
	BigDecimal requirement
) {
}

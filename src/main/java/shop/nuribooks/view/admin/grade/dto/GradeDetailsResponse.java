package shop.nuribooks.view.admin.grade.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record GradeDetailsResponse(

	Integer id,
	String name,
	Integer pointRate,
	BigDecimal requirement
) {
}

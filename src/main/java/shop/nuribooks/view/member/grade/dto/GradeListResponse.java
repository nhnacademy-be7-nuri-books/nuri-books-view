package shop.nuribooks.view.member.grade.dto;

import java.math.BigDecimal;

public record GradeListResponse(

	Integer id,
	String name,
	Integer pointRate,
	BigDecimal requirement
) {}

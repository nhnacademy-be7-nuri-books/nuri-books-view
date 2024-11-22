package shop.nuribooks.view.admin.member.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import shop.nuribooks.view.member.member.entity.GenderType;

public record MemberSearchResponse(

	Long customerId,
	String name,
	GenderType gender,
	String phoneNumber,
	String email,

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDate birthday,

	String username,
	BigDecimal point,
	BigDecimal totalPaymentAmount,
	String authority,
	String gradeName,
	String status,

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime createdAt,

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime latestLoginAt
) {
}

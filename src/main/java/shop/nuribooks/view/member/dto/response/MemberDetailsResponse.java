package shop.nuribooks.view.member.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record MemberDetailsResponse(

	String username,
	String name,
	String phoneNumber,
	String email,
	BigDecimal point,
	BigDecimal totalPaymentAmount,
	String gradeName,
	Integer pointRate,

	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime createdAt

) {}

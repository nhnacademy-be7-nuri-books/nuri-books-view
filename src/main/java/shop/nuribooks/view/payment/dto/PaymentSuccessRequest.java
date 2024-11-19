package shop.nuribooks.view.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record PaymentSuccessRequest(
	String status,
	String orderId,
	String paymentKey,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime requestedAt,
	String method,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime approvedAt,
	Long totalAmount
) {
}

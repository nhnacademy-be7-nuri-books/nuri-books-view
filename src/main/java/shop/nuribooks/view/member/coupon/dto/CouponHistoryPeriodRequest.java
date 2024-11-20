package shop.nuribooks.view.member.coupon.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CouponHistoryPeriodRequest {
	private LocalDate end = LocalDate.now();
	private LocalDate start = end.minusDays(30);
}

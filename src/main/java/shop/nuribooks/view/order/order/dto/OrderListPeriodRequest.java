package shop.nuribooks.view.order.order.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListPeriodRequest {
	private LocalDate end = LocalDate.now();
	private LocalDate start = end.minusDays(30);
}

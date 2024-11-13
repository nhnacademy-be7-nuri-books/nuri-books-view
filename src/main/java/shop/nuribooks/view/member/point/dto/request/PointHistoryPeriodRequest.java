package shop.nuribooks.view.member.point.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryPeriodRequest {
	private LocalDateTime end = LocalDateTime.now();
	private LocalDateTime start = end.minusDays(30);
}

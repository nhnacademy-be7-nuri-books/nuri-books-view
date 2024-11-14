package shop.nuribooks.view.member.point.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryPeriodRequest {
	private LocalDate end = LocalDate.now();
	private LocalDate start = end.minusDays(30);
}

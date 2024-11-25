package shop.nuribooks.view.common.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
	public static long getLeftSecondOfToday(){
		// 현재 시간
		LocalDateTime now = LocalDateTime.now();

		// 자정 시간 계산
		LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1);

		// 자정까지 남은 초 계산
		return ChronoUnit.SECONDS.between(now, midnight);
	}
}

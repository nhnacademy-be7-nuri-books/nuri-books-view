package shop.nuribooks.view.member.point.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.member.point.dto.request.PointHistoryPeriodRequest;
import shop.nuribooks.view.member.point.dto.response.PointHistoryResponse;
import shop.nuribooks.view.member.point.enums.HistoryType;

@FeignClient(name = "point", url = "http://localhost:8080")
public interface PointServiceClient {
	@GetMapping("/api/members/point-history")
	Page<PointHistoryResponse> getPointHistories(
		@RequestParam(value = "type") HistoryType type,
		Pageable pageable,
		PointHistoryPeriodRequest pointHistoryPeriodRequest
	);
}

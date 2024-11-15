package shop.nuribooks.view.member.point.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.member.point.dto.request.PointHistoryPeriodRequest;
import shop.nuribooks.view.member.point.dto.response.PointHistoryResponse;
import shop.nuribooks.view.member.point.enums.HistoryType;

public interface PointService {
	Page<PointHistoryResponse> getPointHistories(
		HistoryType type,
		Pageable pageable,
		PointHistoryPeriodRequest pointHistoryPeriodRequest);
}

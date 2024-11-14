package shop.nuribooks.view.member.point.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.member.point.dto.request.PointHistoryPeriodRequest;
import shop.nuribooks.view.member.point.dto.response.PointHistoryResponse;
import shop.nuribooks.view.member.point.enums.HistoryType;
import shop.nuribooks.view.member.point.feign.PointServiceClient;
import shop.nuribooks.view.member.point.service.PointService;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	private final PointServiceClient pointServiceClient;

	@Override
	public Page<PointHistoryResponse> getPointHistories(HistoryType type, Pageable pageable,
		PointHistoryPeriodRequest pointHistoryPeriodRequest) {
		return pointServiceClient.getPointHistories(type, pageable, pointHistoryPeriodRequest).getBody();
	}
}
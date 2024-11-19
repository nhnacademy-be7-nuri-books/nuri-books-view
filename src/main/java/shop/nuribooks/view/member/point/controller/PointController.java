package shop.nuribooks.view.member.point.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.member.point.dto.request.PointHistoryPeriodRequest;
import shop.nuribooks.view.member.point.dto.response.PointHistoryResponse;
import shop.nuribooks.view.member.point.enums.HistoryType;
import shop.nuribooks.view.member.point.service.PointService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class PointController {
	private final PointService pointService;

	@GetMapping("/my-points")
	public String getPointHistories(
		Model model,
		@RequestParam(value = "type") HistoryType type,
		Pageable pageable,
		PointHistoryPeriodRequest pointHistoryPeriodRequest){
		Page<PointHistoryResponse> points = pointService.getPointHistories(type, pageable, pointHistoryPeriodRequest);
		model.addAttribute("pages", points);
		model.addAttribute("type", type);
		model.addAttribute("period", pointHistoryPeriodRequest);
		return "member/point/history";
	}
}

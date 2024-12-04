package shop.nuribooks.view.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.TopBookResponse;
import shop.nuribooks.view.book.service.BookService;

@RequiredArgsConstructor
@Controller
public class HomeController {
	private final BookService bookService;

	@Operation(summary = "홈 페이지", description = "사용자에게 홈 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "홈 페이지가 성공적으로 반환됨"),
		@ApiResponse(responseCode = "500", description = "서버 오류: 홈 페이지 처리 중 오류 발생")
	})
	@GetMapping("/")
	public String home(Model model) {
		List<TopBookResponse> topLikes = bookService.getTopBookLikes();
		List<List<TopBookResponse>> likeGroups = groupBooks(topLikes, 4);
		model.addAttribute("likeGroups", likeGroups);

		List<TopBookResponse> topScores = bookService.getTopBookScores();
		List<List<TopBookResponse>> scoreGroups = groupBooks(topScores, 4);
		model.addAttribute("scoreGroups", scoreGroups);

		return "main/home";
	}

	private List<List<TopBookResponse>> groupBooks(List<TopBookResponse> books, int groupSize) {
		List<List<TopBookResponse>> groups = new ArrayList<>();

		for (int i = 0; i < books.size(); i += groupSize) {
			int endIndex = Math.min(i + groupSize, books.size());
			List<TopBookResponse> group = books.subList(i, endIndex);
			groups.add(group);
		}

		return groups;
	}
}

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
import shop.nuribooks.view.book.dto.TopBookLikeResponse;
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
		List<TopBookLikeResponse> topLikes = bookService.getTopBookLikes();
		List<List<TopBookLikeResponse>> bookGroups = new ArrayList<>();
		int groupSize = 5;

		for (int i = 0; i < topLikes.size(); i += groupSize) {
			int endIndex = Math.min(i + groupSize, topLikes.size());
			List<TopBookLikeResponse> group = topLikes.subList(i, endIndex);
			bookGroups.add(group);
		}

		model.addAttribute("bookGroups", bookGroups);
		return "main/home";
	}
}

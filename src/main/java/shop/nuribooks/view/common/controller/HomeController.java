package shop.nuribooks.view.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
public class HomeController {

	@Operation(summary = "홈 페이지", description = "사용자에게 홈 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "홈 페이지가 성공적으로 반환됨"),
		@ApiResponse(responseCode = "500", description = "서버 오류: 홈 페이지 처리 중 오류 발생")
	})
	@GetMapping("/")
	public String home() {
		return "main/home";
	}
}

package shop.nuribooks.view.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * 에러 처리 Controller
 *
 * @author nuri
 */
@Controller
public class ErrorController {

	@Value("${error.message-key}")
	private String errorMessageKey;

	@Operation(summary = "에러 처리", description = "에러 발생 시 에러 페이지를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "에러 페이지가 성공적으로 반환됨"),
		@ApiResponse(responseCode = "500", description = "서버 오류: 에러 페이지 처리 중 오류 발생")
	})
	@GetMapping("/error")
	public String error(Model model) {

		String errorMessage = (String)model.getAttribute(errorMessageKey);

		if (errorMessage != null) {
			model.addAttribute(errorMessageKey, errorMessage);
		}

		return "error";
	}
}

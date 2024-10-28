package shop.nuribooks.view.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 에러 처리 Controller
 *
 * @author nuri
 */
@Controller
public class ErrorController {

	@GetMapping("/error")
	public String error() {
		return "error";
	}
}

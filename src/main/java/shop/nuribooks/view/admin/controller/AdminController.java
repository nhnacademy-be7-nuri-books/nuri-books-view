package shop.nuribooks.view.admin.controller;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;

@Controller
public class AdminController {

	/**
	 * admin GET
	 *
	 * @return admin.html
	 */
	@Operation(summary = "관리자 페이지", description = "관리자 페이지를 반환합니다.")
	@GetMapping("/admin")
	public String adminHome() throws IOException {
		return "admin/admin";
	}

}

package shop.nuribooks.view.oauth.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.service.NaverOAuth2ServiceImpl;
import shop.nuribooks.view.oauth.service.PaycoOAuth2ServiceImpl;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OAuth2Controller {

	private final PaycoOAuth2ServiceImpl paycoOAuth2Service;
	private final NaverOAuth2ServiceImpl naverOAuth2Service;

	@Value("${success.message-key}")
	private String messageKey;

	@GetMapping("/login/oauth2/payco")
	public void paycoLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(paycoOAuth2Service.getLoginFormUri());
	}

	@GetMapping("/custom-login/oauth2/code/payco")
	public String doPaycoLogin(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
		paycoOAuth2Service.login(code);
		redirectAttributes.addFlashAttribute(messageKey, "로그인 성공!");
		return "redirect:/";
	}

	@GetMapping("/login/oauth2/naver")
	public void naverLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(naverOAuth2Service.getLoginFormUri());
	}

	@GetMapping("/custom-login/oauth2/code/naver")
	public String doNaverLogin(@RequestParam("code") String code) {
		naverOAuth2Service.login(code);
		return "redirect:/";
	}
}

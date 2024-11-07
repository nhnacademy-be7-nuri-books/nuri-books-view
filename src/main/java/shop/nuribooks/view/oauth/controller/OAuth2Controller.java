package shop.nuribooks.view.oauth.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;
import shop.nuribooks.view.oauth.service.NaverOAuth2ServiceImpl;
import shop.nuribooks.view.oauth.service.PaycoOAuth2ServiceImpl;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OAuth2Controller {
	private final PaycoOAuth2ServiceImpl paycoOAuth2Service;
	private final NaverOAuth2ServiceImpl naverOAuth2Service;

	@GetMapping("/login/oauth2/payco")
	public void paycoLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(paycoOAuth2Service.getLoginFormUri());
	}

	@GetMapping("/custom-login/oauth2/code/payco")
	@ResponseBody
	public String doPaycoLogin(@RequestParam("code") String code) {
		paycoOAuth2Service.login(code);
		return "ok";
	}

	@GetMapping("/login/oauth2/naver")
	public void naverLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(naverOAuth2Service.getLoginFormUri());
	}

	@GetMapping("/custom-login/oauth2/code/naver")
	public String doNaverLogin(@RequestParam("code") String code,
		RedirectAttributes redirectAttributes,
		HttpServletResponse response
		) {
		OAuth2ResultResponse result = naverOAuth2Service.login(code);
		if (result.status().equals("LOGIN_SUCCESS")) {
			List<String> cookies = result.responseMap().get(HttpHeaders.SET_COOKIE);
			// refresh jwt & 그 외 쿠키 저장
			handleCookies(cookies, response);

			// accept jwt 쿠키로 저장
			String authHeader = result.responseMap().get(HttpHeaders.AUTHORIZATION).getFirst();
			if (authHeader != null) {
				String token = authHeader.substring(7);
				addAuthCookie(response, token);
			}

			return "redirect:/";
		} else if (result.status().equals("NEED_REGISTER")) {
			// 회원가입
			return "redirect:/sign-up";
		}
		// 이미 존재하는 정보
		redirectAttributes.addFlashAttribute("이미 가입된 계정이 있습니다.");
		return "redirect:/";
	}

	private void handleCookies(List<String> cookies, HttpServletResponse response) {
		if (cookies != null) {
			for (String cookie : cookies) {
				String[] cookieParts = cookie.split(";");
				String[] keyValue = cookieParts[0].split("=");

				if (keyValue.length == 2) {
					CookieUtil.addCookie(response, keyValue[0].trim(), keyValue[1].trim());
				}
			}
		}
	}

	private void addAuthCookie(HttpServletResponse response, String authHeader) {
		CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, authHeader);
	}
}

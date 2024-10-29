package shop.nuribooks.view.auth.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.auth.dto.request.LoginRequest;
import shop.nuribooks.view.auth.service.AuthService;
import shop.nuribooks.view.common.util.CookieUtil;

/**
 * 인증 관련 Controller
 *
 * @author : nuri
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

	@Value("${error.message-key}")
	private String errorMessageKey;

	/**
	 * 로그인 GET
	 *
	 * @return login.html
	 */
	@GetMapping("/login")
	public String loginForm() throws IOException {
		return "login";
	}

	/**
	 * 로그인 POST
	 *
	 * @param loginRequest form 입력받은 로그인 정보
	 * @param response 로그인 성공 시 받아온 jwt 정보를 쿠키에 저장
	 */
	@PostMapping("/login")
	public String doLogin(@Valid @ModelAttribute LoginRequest loginRequest,
		RedirectAttributes redirectAttributes,
		HttpServletResponse response) {

		Map<String, List<String>> result = authService.login(loginRequest);

		// 에러 메시지 처리
		if (result.containsKey(errorMessageKey)) {
			String errorMessageStr = result.get(errorMessageKey).getFirst();
			log.error("Login failed: {}", errorMessageStr);
			redirectAttributes.addFlashAttribute(errorMessageKey, errorMessageStr);
			return "redirect:/login";
		} else {

			List<String> cookies = result.get(HttpHeaders.SET_COOKIE);
			// refresh jwt & 그 외 쿠키 저장
			handleCookies(cookies, response);

			// accept jwt 쿠키로 저장
			String authHeader = result.get(HttpHeaders.AUTHORIZATION).getFirst();
			if (authHeader != null) {
				// "Bearer "
				String token = authHeader.substring(7);
				addAuthCookie(response, token);
			}

			log.info("로그인 성공");
		}

		String authHeader = result.get("X-USER-ID").getFirst();
		redirectAttributes.addFlashAttribute("userID", authHeader);
		return "redirect:/";

	}

	/**
	 * SET-COOKIE 로 얻어온 쿠키 목록을 처리
	 *
	 * @param cookies "name=value; attributes" 형식의 쿠키 목록
	 * @param response 쿠키가 추가 될 HttpServletResponse
	 */
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

	/**
	 * AUTHORIZATION 로 얻어올 jwt 정보
	 *
	 * @param response 쿠키가 추가 될 HttpServletResponse
	 * @param authHeader AUTHORIZATION 헤더 내용
	 */
	private void addAuthCookie(HttpServletResponse response, String authHeader) {
		CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, authHeader);
	}

}

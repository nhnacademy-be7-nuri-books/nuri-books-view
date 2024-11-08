package shop.nuribooks.view.oauth.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.type.OAuth2Status;
import shop.nuribooks.view.oauth.common.utils.CookieUtils;
import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;
import shop.nuribooks.view.oauth.service.NaverOAuth2ServiceImpl;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NaverOAuth2Controller {
	private final NaverOAuth2ServiceImpl naverOAuth2Service;

	@Value("${error.message-key}")
	private String errorMessageKey;

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
		if (result.getStatus().equals(OAuth2Status.LOGIN_SUCCESS.toString())) {
			List<String> cookies = result.getResponseMap().get(HttpHeaders.SET_COOKIE);
			// refresh jwt & 그 외 쿠키 저장
			CookieUtils.handleCookies(cookies, response);
			// accept jwt 쿠키로 저장
			String authHeader = result.getResponseMap().get(HttpHeaders.AUTHORIZATION).getFirst();
			if (authHeader != null) {
				String token = authHeader.substring(7);
				CookieUtils.addAuthCookie(response, token);
			}
			return "redirect:/";
		} else if (result.getStatus().equals(OAuth2Status.NEED_REGISTER.toString())) {
			// 회원가입
			redirectAttributes.addFlashAttribute("id", result.getResponseMap().get("userInfo").get(0));
			redirectAttributes.addFlashAttribute("email", result.getResponseMap().get("userInfo").get(1));
			return "redirect:/simple-signup";
		} else {
			// 이미 존재하는 정보
			redirectAttributes.addFlashAttribute(errorMessageKey, "해당 이메일로 가입된 일반 계정이 존재합니다.");
			return "redirect:/login";
		}
	}
}

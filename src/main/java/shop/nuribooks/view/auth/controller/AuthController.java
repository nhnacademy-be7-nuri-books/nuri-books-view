package shop.nuribooks.view.auth.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.auth.dto.request.AuthenticationCodeRequest;
import shop.nuribooks.view.auth.dto.request.LoginRequest;
import shop.nuribooks.view.auth.dto.request.MemberReactiveRequest;
import shop.nuribooks.view.auth.service.AuthService;
import shop.nuribooks.view.common.decoder.JwtDecoder;
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

	public static final String REDIRECT_HOME = "redirect:/";

	private final AuthService authService;

	@Value("${error.message-key}")
	private String errorMessageKey;
	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;
	@Value("${success.message-key}")
	private String successMessageKey;

	/**
	 * 로그인 GET
	 *
	 * @return login.html
	 */
	@Operation(summary = "로그인 폼", description = "로그인 페이지를 반환합니다.")
	@GetMapping("/login")
	public String loginForm() throws IOException {
		return "auth/login";
	}

	/**
	 * 로그인 POST
	 *
	 * @param loginRequest form 입력받은 로그인 정보
	 * @param response 로그인 성공 시 받아온 jwt 정보를 쿠키에 저장
	 */
	@Operation(summary = "로그인", description = "사용자의 로그인 요청을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "302", description = "로그인 성공"),
		@ApiResponse(responseCode = "302", description = "로그인 실패"),
		@ApiResponse(responseCode = "500", description = "서버 오류: 로그인 처리 중 오류 발생")
	})
	@PostMapping("/login")
	public String doLogin(@Valid @ModelAttribute LoginRequest loginRequest,
		RedirectAttributes redirectAttributes,
		HttpServletResponse response) {

		Map<String, List<String>> result = authService.login(loginRequest);
		String authHeader = null;

		// 에러 메시지 처리
		// 휴면 회원의 경우
		if (result.containsKey(errorMessageKey)) {
			if (result.get(errorMessageKey).getFirst().contains("휴면")) {
				redirectAttributes.addFlashAttribute("username", loginRequest.username());
				return "redirect:/login/inactive";
			}

			String errorMessageStr = result.get(errorMessageKey).getFirst();
			log.error("Login 실패: {}", errorMessageStr);
			redirectAttributes.addFlashAttribute(errorMessageKey, errorMessageStr);
			return "redirect:/login";
		} else {

			List<String> cookies = result.get(HttpHeaders.SET_COOKIE);
			// refresh jwt & 그 외 쿠키 저장
			handleCookies(cookies, response);

			// accept jwt 쿠키로 저장
			authHeader = result.get(HttpHeaders.AUTHORIZATION).getFirst();
			if (authHeader != null) {
				String token = authHeader.substring(7);
				addAuthCookie(response, token);
			}

			log.info("로그인 성공");
		}

		redirectAttributes.addFlashAttribute(successMessageKey, "로그인 성공, 환영합니다.");

		String userRole = JwtDecoder.getRole(authHeader);

		if (userRole.contains("ADMIN")) {
			return "redirect:/admin";
		}

		return REDIRECT_HOME;

	}

	/**
	 * POST 로그아웃
	 *
	 * @return 반환 경로
	 */
	@Operation(summary = "로그아웃", description = "사용자의 로그아웃 요청을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "302", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "302", description = "로그아웃 실패"),
		@ApiResponse(responseCode = "500", description = "서버 오류: 로그아웃 처리 중 오류 발생")
	})
	@PostMapping("/logout")
	public String doLogout(HttpServletResponse response,
		RedirectAttributes redirectAttributes) {
		String result = authService.logout();

		if (result.equals(successMessageKey)) {
			CookieUtil.deleteCookie(response, HttpHeaders.AUTHORIZATION);
			CookieUtil.deleteCookie(response, refreshHeaderName);
			redirectAttributes.addFlashAttribute(successMessageKey, "로그아웃에 성공하였습니다.");
			log.info("로그아웃 성공");
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute(errorMessageKey, "로그아웃 실패: " + result);
			log.info("로그아웃 실패");
			return "redirect:/";
		}
	}

	@GetMapping("/login/inactive")
	public String inactiveLoginForm(@ModelAttribute(value = "username") String username, Model model) {

		String hookUrl = "https://hook.dooray.com/services/3204376758577275363/3940128695610713143/D2Wa5K8hTI2yixx1eLi2ug";
		model.addAttribute("hookUrl", hookUrl);
		model.addAttribute("username", username);

		return "auth/inactive-login";
	}

	@PostMapping("/login/inactive-code")
	@ResponseBody
	public String sendAuthenticationCode(@ModelAttribute AuthenticationCodeRequest request) {

		return authService.sendAuthenticationCode(request);
	}

	@PostMapping("/login/inactive")
	public String memberReactive(@ModelAttribute MemberReactiveRequest request, Model model) {

		authService.reactiveMember(request);

		model.addAttribute("successMessage", "휴면회원 인증이 완료되었습니다.");

		return "redirect:/login";
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

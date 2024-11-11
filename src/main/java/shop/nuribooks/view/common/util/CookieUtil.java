package shop.nuribooks.view.common.util;

import java.util.Objects;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 쿠키 관련 유틸리티
 *
 * @author nuri
 */
public class CookieUtil {

	/**
	 * HTTP 응답에 쿠키 추가
	 *
	 * @param response 쿠키가 추가 될 HttpServletResponse
	 * @param name 쿠키 이름
	 * @param value 쿠키 값
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 쿠키를 삭제
	 *
	 * @param response {@link HttpServletResponse} 응답 객체
	 * @param cookieName 삭제할 쿠키의 이름
	 */
	public static void deleteCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 특정 쿠키 찾기
	 * @param request {@link HttpServletRequest}
	 * @param key 쿠키 이름
	 * @return 쿠키 값
	 */
	public static String findByCookieKey(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();

		if (Objects.nonNull(cookies)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}

}
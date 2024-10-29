package shop.nuribooks.view.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
}

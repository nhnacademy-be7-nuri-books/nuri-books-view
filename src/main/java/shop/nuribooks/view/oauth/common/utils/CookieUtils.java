package shop.nuribooks.view.oauth.common.utils;

import java.util.List;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletResponse;
import shop.nuribooks.view.common.util.CookieUtil;

public class CookieUtils {
	public static void handleCookies(List<String> cookies, HttpServletResponse response) {
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

	public static void addAuthCookie(HttpServletResponse response, String authHeader) {
		CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, authHeader);
	}
}

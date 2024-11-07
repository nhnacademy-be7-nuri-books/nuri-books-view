package shop.nuribooks.view.common.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그인 체크하는 Interceptor
 *
 * @author nuri
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

	/**
	 * 요청마다 토큰이 있다면 로그인 했음을 확인
	 *
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @param object object 핸들러 객체
	 * @return 다음 인터셉터 또는 핸들러 요청으로 진행하기 위해 항상 true
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
		boolean isLoggedIn = false;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (HttpHeaders.AUTHORIZATION.equals(cookie.getName())) {
					isLoggedIn = true;
					break;
				}
			}
		}

		request.setAttribute("isLoggedIn", isLoggedIn);
		return true;

	}
}
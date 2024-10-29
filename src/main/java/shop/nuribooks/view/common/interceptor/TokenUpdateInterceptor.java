package shop.nuribooks.view.common.interceptor;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.nuribooks.view.common.util.CookieUtil;

/**
 * 응답의 토큰을 재발행하고 쿠키를 업데이트하는 Interceptor
 *
 * @author nuri
 */
@Component
public class TokenUpdateInterceptor implements HandlerInterceptor {

	/**
	 * 매 응답마다 토큰 재발행이 되었으면 이를 쿠키에 갱신
	 *
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @param handler 요청을 처리하는 핸들러 객체
	 * @param modelAndView 응답 모델과 뷰 정보를 포함하는 객체
	 * @throws Exception 예외가 발생한 경우
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

		String refreshToken = "";
		String acceptToken = response.getHeader(HttpHeaders.AUTHORIZATION);

		if (Objects.nonNull(acceptToken)) {

			String[] cookies = response.getHeader(HttpHeaders.SET_COOKIE).split(";");

			for (String cookie : cookies) {
				String[] cookieParse = cookie.split("=");
				if (cookieParse[0].equals("Refresh")) {
					refreshToken = cookieParse[1];
				}
			}

			CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, acceptToken);
			CookieUtil.addCookie(response, "Refresh", refreshToken);
		}
	}
}

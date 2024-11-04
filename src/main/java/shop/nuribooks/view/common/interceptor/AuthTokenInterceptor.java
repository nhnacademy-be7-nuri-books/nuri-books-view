package shop.nuribooks.view.common.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 로그인 정보 전달 RequestInterceptor
 * 모든 Feign Client 의 요청마다 처리
 *
 * @author nuri
 */
@Component
public class AuthTokenInterceptor implements RequestInterceptor {

	private final HttpServletRequest request;
	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;

	public AuthTokenInterceptor(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * jwt 정보가 있을 시 이를 헤더 정보에 포함해서 요청하는 Method
	 *
	 * @param requestTemplate Feign 에서 HTTP 요청을 구성하는 데 사용되는 클래스
	 */
	@Override
	public void apply(RequestTemplate requestTemplate) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
					requestTemplate.header(HttpHeaders.AUTHORIZATION, cookie.getValue());
				} else if (cookie.getName().equals(refreshHeaderName)) {
					requestTemplate.header(refreshHeaderName, cookie.getValue());
				}
			}
		}
	}
}
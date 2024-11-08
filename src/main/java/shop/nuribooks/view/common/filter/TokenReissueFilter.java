package shop.nuribooks.view.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.decoder.JwtDecoder;
import shop.nuribooks.view.common.feign.ReissueServiceClient;
import shop.nuribooks.view.common.util.CookieUtil;

/**
 * 토큰 재발행 검사 로직
 *
 * @author nuri
 */
@Component
@WebFilter(urlPatterns = "/**")
@RequiredArgsConstructor
public class TokenReissueFilter implements Filter {

	private final ReissueServiceClient reissueServiceClient;

	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;

		String prevAccessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);
		String prevRefreshToken = CookieUtil.findByCookieKey(request, refreshHeaderName);

		if (Objects.nonNull(prevRefreshToken)) {
			if (Objects.isNull(prevAccessToken) || JwtDecoder.isExpired(prevAccessToken) && !JwtDecoder.isExpired(
				prevRefreshToken)) {
				ResponseEntity<String> reissueResponse = reissueServiceClient.reissue();
				HttpHeaders headers = reissueResponse.getHeaders();

				String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

				Map<String, List<String>> responseMap = new HashMap<>();
				Optional.ofNullable(headers.get(HttpHeaders.SET_COOKIE))
					.filter(list -> !list.isEmpty())
					.ifPresent(setCookieHeaders -> responseMap.put(HttpHeaders.SET_COOKIE, setCookieHeaders));

				List<String> cookies = responseMap.get(HttpHeaders.SET_COOKIE);
				handleCookies(cookies, response);
				CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, accessToken);
			}
		}

		chain.doFilter(request, response);
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

}

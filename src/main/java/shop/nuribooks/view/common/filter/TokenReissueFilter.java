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
import org.springframework.web.filter.OncePerRequestFilter;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.decoder.JwtDecoder;
import shop.nuribooks.view.common.feign.ReissueServiceClient;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.common.util.ExceptionUtil;

/**
 * 토큰 재발행 검사 로직
 *
 * @author nuri
 */
@Component
@WebFilter(urlPatterns = "/**")
@RequiredArgsConstructor
@Slf4j
public class TokenReissueFilter extends OncePerRequestFilter {

	private final ReissueServiceClient reissueServiceClient;

	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String prevAccessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);
		String prevRefreshToken = CookieUtil.findByCookieKey(request, refreshHeaderName);

		if (Objects.nonNull(prevAccessToken) && (Objects.isNull(prevRefreshToken) || JwtDecoder.isExpired(prevRefreshToken))) {
			log.info("Access는 존재하지만 Refresh가 없거나 만료되어 로그아웃처리합니다.");
			logout(response);
			response.sendRedirect("/login");
			return;
		}

		if (Objects.nonNull(prevRefreshToken)) {
			if (Objects.isNull(prevAccessToken) || (JwtDecoder.isExpired(prevAccessToken) && !JwtDecoder.isExpired(
				prevRefreshToken))) {

				try {
					ResponseEntity<String> reissueResponse = reissueServiceClient.reissue(prevRefreshToken);
					HttpHeaders headers = reissueResponse.getHeaders();
					String accessToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

					Map<String, List<String>> responseMap = new HashMap<>();
					Optional.ofNullable(headers.get(HttpHeaders.SET_COOKIE))
						.filter(list -> !list.isEmpty())
						.ifPresent(setCookieHeaders -> responseMap.put(HttpHeaders.SET_COOKIE, setCookieHeaders));

					List<String> cookies = responseMap.get(HttpHeaders.SET_COOKIE);
					handleCookies(cookies, response);
					CookieUtil.addCookie(response, HttpHeaders.AUTHORIZATION, accessToken);
				} catch (FeignException e) {
					logout(response);
					response.sendRedirect("/login");
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
	}

	private void logout(HttpServletResponse response) {
		CookieUtil.deleteCookie(response, "Authorization");
		CookieUtil.deleteCookie(response, "Refresh");
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

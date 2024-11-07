package shop.nuribooks.view.common.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.decoder.JwtDecoder;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.exception.UnauthorizedException;

/**
 * 어드민 권한 확인 필터
 *
 * @author taek
 */
@Slf4j
public class AdminCheckFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (!request.getRequestURI().matches("^/admin(/.*)?$")) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);

		if (Objects.isNull(accessToken)) {
			response.sendRedirect("/login");
			return;
		}

		if (!JwtDecoder.getRole(accessToken).contains("ADMIN")) {
			throw new UnauthorizedException();
		}

		filterChain.doFilter(request, response);
	}
}

package shop.nuribooks.view.common.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
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
@Component
@WebFilter("/admin/**")
@Slf4j
public class AdminCheckFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (!request.getRequestURI().matches("^/admin(/.*)?$")) {
			log.info("/admin/** 이 아니어서 통과합니다.");
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);

		if (Objects.isNull(accessToken)) {
			log.info("/admin 경로 인가를 위한 access Token이 존재하지 않습니다.");
			response.sendRedirect("/login");
			return;
		}

		if (!JwtDecoder.getRole(accessToken).contains("ADMIN")) {
			log.info("/admin을 요청했으나 ADMIN 권한이 없습니다.");
			response.sendRedirect("/login");
			return;
		}

		log.info("/admin에 대한 유효한 admin입니다.");
		filterChain.doFilter(request, response);
	}
}

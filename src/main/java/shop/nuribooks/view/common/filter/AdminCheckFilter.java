package shop.nuribooks.view.common.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminCheckFilter extends OncePerRequestFilter {
	// JwtDecoder 주입

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (!request.getRequestURI().matches("^/admin(/.*)?$")) {
			filterChain.doFilter(request, response);
			return;
		}

		// TODO (누리님): JwtDecoder로 처리해주세요
		// 1. Access Token => response.sendRedirect(/login);

		// 2. role => ROLE_ADMIN이 아니면 401 Page?

		filterChain.doFilter(request, response);
	}
}

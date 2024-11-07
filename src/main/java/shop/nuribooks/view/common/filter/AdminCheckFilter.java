package shop.nuribooks.view.common.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.util.JwtUtils;

@Slf4j
public class AdminCheckFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;

	public AdminCheckFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (!request.getRequestURI().matches("^/admin(/.*)?$")) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = request.getHeader("Authorization");
		// Access 토큰이 없는 경우
		if (accessToken == null || accessToken.isBlank() || !accessToken.startsWith("Bearer ")) {
			log.info("Access Token이 존재하지않습니다.");
			response.sendRedirect("/login");
			return;
		}

		String validAccessToken = accessToken.split(" ")[1];
		// Access 토큰이 만료된 경우
		if (jwtUtils.isExpired(validAccessToken)) {
			log.info("Access Token이 만료되었습니다. 재발급받으십시오.");
			response.sendRedirect("/login");
			return;
		}

		String role = jwtUtils.getRole(validAccessToken);
		if (!role.equals("ROLE_ADMIN")) {
			response.sendRedirect("/login");
			return;
		}

		filterChain.doFilter(request, response);
	}
}

package shop.nuribooks.view.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * cors 설정
 *
 * <p>
 * Cross-Origin Resource Sharing (CORS) 정책을 설정하여
 * 특정 출처에서의 요청을 허용
 * </p>
 *
 * @author nuri
 */
@Configuration
public class CorsConfig {

	/**
	 * CORS 필터를 설정하는 빈 메소드
	 *
	 * @return CORS 요청을 처리하는 {@link CorsFilter} 객체
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedOrigin("http://localhost:8081");
		config.addAllowedOrigin("http://localhost:8083");
		config.addAllowedOrigin("https://nuribooks.shop");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}


package shop.nuribooks.view.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import shop.nuribooks.view.common.interceptor.LoginCheckInterceptor;
import shop.nuribooks.view.common.interceptor.TokenUpdateInterceptor;

/**
 * Spring Web 를 구성하는 설정 클래스
 *
 * @author nuri
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final LoginCheckInterceptor loginCheckInterceptor;
	private final TokenUpdateInterceptor tokenUpdateInterceptor;

	/**
	 * 생성자
	 *
	 * @param loginCheckInterceptor 로그인 체크 인터셉터
	 * @param tokenUpdateInterceptor 토큰 재발행 인터셉터
	 */
	public WebConfig(LoginCheckInterceptor loginCheckInterceptor,
		TokenUpdateInterceptor tokenUpdateInterceptor) {
		this.loginCheckInterceptor = loginCheckInterceptor;
		this.tokenUpdateInterceptor = tokenUpdateInterceptor;
	}

	/**
	 * 인터셉터 추가
	 *
	 * @param registry registry 인터셉터를 등록하는 데 사용되는 {@link InterceptorRegistry} 객체
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 로그인 체크 인터셉터 등록
		registry.addInterceptor(loginCheckInterceptor)
			.excludePathPatterns("/eureka/**", "/static/**", "/error")
			.addPathPatterns("/**");

		// 토큰 재발행 인터셉터 등록
		registry.addInterceptor(tokenUpdateInterceptor)
			.excludePathPatterns("/eureka/**", "/static/**", "/error")
			.addPathPatterns("/**");

	}
}
package shop.nuribooks.view.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.nuribooks.view.common.feign.ReissueServiceClient;
import shop.nuribooks.view.common.filter.AdminCheckFilter;
import shop.nuribooks.view.common.filter.TokenReissueFilter;

@Configuration
public class FilterConfig {

	@Autowired
	private ReissueServiceClient reissueServiceClient;

	@Bean
	public FilterRegistrationBean<TokenReissueFilter> loggingFilter() {
		FilterRegistrationBean<TokenReissueFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new TokenReissueFilter(reissueServiceClient));
		registrationBean.addUrlPatterns("/**");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<AdminCheckFilter> customAdminCheckFilter() {
		FilterRegistrationBean<AdminCheckFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AdminCheckFilter());
		registrationBean.addUrlPatterns("/admin/**");
		registrationBean.setOrder(2);
		return registrationBean;
	}
}

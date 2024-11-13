package shop.nuribooks.view.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.httpclient.ApacheHttpClient;

@Configuration
public class FeignConfig {
	@Bean
	public feign.Client feignClient() {
		return new ApacheHttpClient();
	}
}

package shop.nuribooks.view.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.httpclient.ApacheHttpClient;

/**
 * Feign에서 Patch 메서드를 사용하기 위한 Config
 */
@Configuration
public class OpenFeignConfig {

	@Bean
	public Client client() {
		return new ApacheHttpClient();
	}
}

package shop.nuribooks.view.oauth.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OAuth2ClientProperties {
	private final Registration registration = new Registration();
	private final Provider provider = new Provider();

	@Getter
	public static class Registration {
		private final Payco payco = new Payco();
		private final Naver naver = new Naver();

		@Getter
		@Setter
		public static class Payco {
			private String clientName;
			private String clientId;
			private String clientSecret;
			private String redirectUri;
			private String authorizationGrantType;
			private String scope;
		}

		@Getter
		@Setter
		public static class Naver {
			private String clientName;
			private String clientId;
			private String clientSecret;
			private String redirectUri;
			private String authorizationGrantType;
			private String scope;
		}
	}

	@Getter
	public static class Provider {
		private final Payco payco = new Payco();
		private final Naver naver = new Naver();

		@Getter
		@Setter
		public static class Payco {
			private String authorizationUri;
			private String tokenUri;
			private String userInfoUri;
		}

		@Getter
		@Setter
		public static class Naver {
			private String authorizationUri;
			private String tokenUri;
			private String userInfoUri;
			private String userNameAttribute;
		}
	}
}

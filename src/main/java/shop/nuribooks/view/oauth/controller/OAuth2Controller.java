package shop.nuribooks.view.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;

@Slf4j
@Controller
public class OAuth2Controller {
	private final OAuth2ClientProperties oAuth2ClientProperties;

	public OAuth2Controller(OAuth2ClientProperties oAuth2ClientProperties) {
		this.oAuth2ClientProperties = oAuth2ClientProperties;
	}

	@GetMapping("/login/oauth2/payco")
	public void paycoLogin(HttpServletResponse response) {
		log.info("{}", oAuth2ClientProperties.getRegistration().getPayco().getClientSecret());
	}
}

package shop.nuribooks.view.oauth.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.oauth.common.property.OAuth2ClientProperties;
import shop.nuribooks.view.oauth.service.OAuth2Service;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OAuth2Controller {
	private final OAuth2Service oAuth2Service;

	@GetMapping("/login/oauth2/payco")
	public void paycoLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(oAuth2Service.getPaycoLoginFormUri());
	}

	@GetMapping("/custom-login/oauth2/code/payco")
	@ResponseBody
	public String doPaycoLogin(@RequestParam("code") String code) {
		oAuth2Service.paycoLogin(code);
		return "ok";
	}
}

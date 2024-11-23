package shop.nuribooks.view.oauth.service;

import shop.nuribooks.view.oauth.dto.OAuth2ResultResponse;

public interface OAuth2Service {
	String getLoginFormUri();

	OAuth2ResultResponse login(String code);
}

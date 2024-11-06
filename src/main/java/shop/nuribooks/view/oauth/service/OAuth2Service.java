package shop.nuribooks.view.oauth.service;

import java.util.Optional;

import shop.nuribooks.view.oauth.dto.PaycoUser;

public interface OAuth2Service {
	String getLoginFormUri();
	Optional<PaycoUser> login(String code);
}

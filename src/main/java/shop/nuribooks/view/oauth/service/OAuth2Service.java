package shop.nuribooks.view.oauth.service;

import java.util.Optional;

import shop.nuribooks.view.oauth.dto.OAuth2UserResponse;

public interface OAuth2Service {
	String getLoginFormUri();
	Optional<OAuth2UserResponse> login(String code);
}

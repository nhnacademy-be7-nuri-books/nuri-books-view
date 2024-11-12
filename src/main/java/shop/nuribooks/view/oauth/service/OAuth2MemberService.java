package shop.nuribooks.view.oauth.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import shop.nuribooks.view.member.feign.MemberServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.member.dto.response.MemberRegisterResponse;
import shop.nuribooks.view.oauth.dto.OAuth2RegisterRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2MemberService {
	private final MemberServiceClient memberServiceClient;

	@Value("${success.message-key}")
	private String successMessageKey;

	public String signup(OAuth2RegisterRequest registerRequest) {
		// TODO: 비밀번호 난수로 생성
		registerRequest.setPassword("11111111");
		registerRequest.setUsername(registerRequest.getUsername());
		try {
			MemberRegisterResponse response = memberServiceClient.registerUser(registerRequest.toRecord()).getBody();
			return successMessageKey + Objects.requireNonNull(response).username();
		} catch (FeignException ex) {
			return ExceptionUtil.handleFeignException(ex);
		}

	}
}

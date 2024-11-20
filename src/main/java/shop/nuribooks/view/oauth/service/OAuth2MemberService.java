package shop.nuribooks.view.oauth.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.member.member.dto.response.MemberRegisterResponse;
import shop.nuribooks.view.member.member.feign.MemberServiceClient;
import shop.nuribooks.view.oauth.common.utils.SecureRandomGenerator;
import shop.nuribooks.view.oauth.dto.OAuth2RegisterRequest;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2MemberService {
    private final MemberServiceClient memberServiceClient;

    @Value("${success.message-key}")
    private String successMessageKey;

    public String signup(OAuth2RegisterRequest registerRequest) {
        registerRequest.setPassword(SecureRandomGenerator.generateRandomString(15));
        log.info("password : {}", registerRequest.getPassword());
        try {
            MemberRegisterResponse response = memberServiceClient.registerUser(registerRequest.toRecord()).getBody();
            return successMessageKey + Objects.requireNonNull(response).username();
        } catch (FeignException ex) {
            return ExceptionUtil.handleFeignException(ex);
        }
    }
}

package shop.nuribooks.view.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.auth.dto.request.NonMemberRequest;
import shop.nuribooks.view.auth.dto.response.NonMemberResponse;
import shop.nuribooks.view.common.feign.AuthServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;

@RequiredArgsConstructor
@Service
public class NonmemberService {
	private final AuthServiceClient authServiceClient;
	@Value("${success.message-key}")
	private String successMessageKey;

	@Value("${error.message-key}")
	private String errorMessageKey;

	public String checkNonMember(NonMemberRequest loginRequest) {
		try {
			NonMemberResponse nonMemberResponse = authServiceClient.checkNonMember(loginRequest).getBody();
			if (nonMemberResponse != null && nonMemberResponse.email() != null) {
				return successMessageKey + " " + nonMemberResponse.customerId();
			} else {
				return errorMessageKey;
			}
		} catch (FeignException ex) {
			return ExceptionUtil.handleFeignException(ex);
		}
	}
}

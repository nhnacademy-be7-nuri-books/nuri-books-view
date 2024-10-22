package shop.nuribooks.view.service.member;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.feign.MemberServiceClient;
import shop.nuribooks.view.dto.common.ResponseMessage;
import shop.nuribooks.view.dto.member.request.MemberCreateRequest;
import shop.nuribooks.view.exception.member.MemberRegisterFailedException;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberServiceClient memberServiceClient;

	public ResponseMessage registerUser(@Valid MemberCreateRequest userRequest) {
		ResponseEntity<ResponseMessage> response = memberServiceClient.registerUser(userRequest);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new MemberRegisterFailedException("회원가입 실패");
		}
	}
}

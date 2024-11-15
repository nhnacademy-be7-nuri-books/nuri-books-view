package shop.nuribooks.view.member.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.dto.request.MemberPasswordUpdateRequest;
import shop.nuribooks.view.member.dto.response.MemberDetailsResponse;
import shop.nuribooks.view.member.feign.MemberServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.dto.response.MemberRegisterResponse;

/**
 * 회원 서비스 구현체
 *
 * @author : nuri
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberServiceClient memberServiceClient;

	@Value("${success.message-key}")
	private String successMessageKey;

	/**
	 * 사용자를 등록하는 메소드.
	 *
	 * @param userRequest 등록할 사용자 정보가 담긴 {@link MemberRegisterRequest} 객체
	 * @return 등록 성공 시 사용자 ID를 포함한 성공 메시지
	 * @throws FeignException Feign 클라이언트에서 발생하는 예외
	 */
	public String registerUser(@Valid MemberRegisterRequest userRequest) {
		try {
			MemberRegisterResponse response = memberServiceClient.registerUser(userRequest).getBody();
			return successMessageKey + Objects.requireNonNull(response).username();
		} catch (FeignException ex) {
			return ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public MemberDetailsResponse getMemberDetails() {

		ResponseEntity<MemberDetailsResponse> response = memberServiceClient.getMemberDetails();

		return response.getBody();
	}

	@Override
	public ResponseMessage memberPasswordUpdate(MemberPasswordUpdateRequest request) {

		return memberServiceClient.memberPasswordUpdate(request).getBody();
	}

	@Override
	public Integer getMemberDetailsBeforeWithdraw() {

		BigDecimal point = memberServiceClient.getMemberDetails().getBody().point();

		// point가 null일 경우 0 반환
		return point != null ? point.intValue() : 0;
	}

	@Override
	public ResponseMessage memberWithdraw() {

		return memberServiceClient.memberWithdraw().getBody();
	}
}

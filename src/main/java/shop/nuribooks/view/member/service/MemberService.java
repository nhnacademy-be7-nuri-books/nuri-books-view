package shop.nuribooks.view.member.service;

import jakarta.validation.Valid;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.dto.request.MemberRegisterRequest;
import shop.nuribooks.view.member.dto.request.MemberUpdateRequest;
import shop.nuribooks.view.member.dto.response.MemberDetailsResponse;

/**
 * 회원 서비스 인터페이스.
 *
 * @author nuri
 */
public interface MemberService {
	/**
	 *
	 * 회원 등록
	 *
	 * @param userRequest 등록할 회원의 정보 {@link MemberRegisterRequest}
	 * @return 등록 성공 시 사용자 ID를 포함한 메세지,
	 *         실패 시 오류 매세지
	 */
	String registerUser(@Valid MemberRegisterRequest userRequest);

	MemberDetailsResponse getMemberDetails();

	MemberUpdateRequest getMemberDetailsBeforeUpdate();

	ResponseMessage memberUpdate(MemberUpdateRequest request);

	Integer getMemberDetailsBeforeWithdraw();
}

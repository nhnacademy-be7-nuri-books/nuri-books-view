package shop.nuribooks.view.service.member;

import jakarta.validation.Valid;
import shop.nuribooks.view.dto.member.request.MemberRegisterRequest;

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
	 * @return 등록 성곡 시 사용자 ID를 포함한 메세지,
	 *         실패 시 오류 매세지
	 */
	String registerUser(@Valid MemberRegisterRequest userRequest);
}

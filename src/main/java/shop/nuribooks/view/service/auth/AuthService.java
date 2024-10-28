package shop.nuribooks.view.service.auth;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import shop.nuribooks.view.dto.auth.request.LoginRequest;

/**
 * 인증 서비스 인터페이스.
 *
 * @author nuri
 */
public interface AuthService {

	/**
	 *
	 * 로그인
	 *
	 * @param loginRequest 등록할 회원의 정보 {@link LoginRequest}
	 * @return 등록 성공 시 사용자 ID를 포함한 메세지,
	 *         실패 시 오류 매세지
	 */
	Map<String, List<String>> login(@Valid LoginRequest loginRequest);
}

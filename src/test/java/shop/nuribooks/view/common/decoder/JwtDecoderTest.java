package shop.nuribooks.view.common.decoder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtDecoderTest {

	private String validToken = "header.eyJ0b2tlblR5cGUiOiJBY2Nlc3MiLCJ1c2VySWQiOiIyMjIyMjIyMiIsInJvbGUiOiJST0xFX01FTUJFUiIsImlhdCI6MTczMDk2NDkxOCwiZXhwIjoxNzY3MjI1NjAwLCJpc3N1ZXIiOiJudXJpYm9va3MtYXV0aCJ9.signature";
	private String expiredToken = "header.eyJ0b2tlblR5cGUiOiJBY2Nlc3MiLCJ1c2VySWQiOiIyMjIyMjIyMiIsInJvbGUiOiJST0xFX01FTUJFUiIsImlhdCI6MTczMDk2NDkxOCwiZXhwIjoxNTc3ODM2ODAwLCJpc3N1ZXIiOiJudXJpYm9va3MtYXV0aCIsImFsZyI6IkhTMjU2In0.signature";

	@Test
	@DisplayName("사용자 PK 얻어오기")
	public void teatTokenUsername() {
		String username = JwtDecoder.getUserId(validToken);
		assertEquals(username, "22222222");
	}

	@Test
	@DisplayName("사용자 ROLE 얻어오기")
	public void teatTokenRole() {
		String role = JwtDecoder.getRole(validToken);
		assertEquals(role, "ROLE_MEMBER");
	}

	@Test
	@DisplayName("권한 유효한 토큰 만료 확인 - false")
	public void teatTokenExpiredValidToken() {
		boolean isExpired = JwtDecoder.isExpired(validToken);
		assertFalse(isExpired);
	}

	@Test
	@DisplayName("권한 유효한 토큰 만료 확인 - true")
	public void teatTokenExpiredExpiredToken() {
		boolean isExpired = JwtDecoder.isExpired(expiredToken);
		assertTrue(isExpired);
	}

}
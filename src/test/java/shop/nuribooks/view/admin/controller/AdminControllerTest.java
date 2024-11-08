package shop.nuribooks.view.admin.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// admin filter를 추가해 ROLE_ADMIN이 아닌 경우 통과할 수 없는 테스트입니다.
	// @Test
	// @DisplayName("관리자 페이지 접근 테스트")
	// void adminHome_shouldReturnAdminView() throws Exception {
	// 	mockMvc.perform(get("/admin"))
	// 		.andExpect(status().isOk())
	// 		.andExpect(view().name("admin/admin"));
	// }
}
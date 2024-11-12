// package shop.nuribooks.view.admin.controller;
//
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.test.web.servlet.MockMvc;
//
// import shop.nuribooks.view.member.feign.MemberServiceClient;
//
// @WebMvcTest(AdminController.class)
// @EnableFeignClients(basePackages = "shop.nuribooks.view.common.feign")
// class AdminControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@Autowired
// 	private MemberServiceClient memberServiceClient;
//
// 	@Test
// 	@DisplayName("관리자 페이지 접근 테스트")
// 	void adminHome_shouldReturnAdminView() throws Exception {
// 		mockMvc.perform(get("/admin"))
// 			.andExpect(status().isOk())
// 			.andExpect(view().name("admin/admin"));
// 	}
// }
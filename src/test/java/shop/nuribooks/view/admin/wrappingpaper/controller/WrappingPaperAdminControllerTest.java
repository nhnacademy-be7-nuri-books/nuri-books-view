package shop.nuribooks.view.admin.wrappingpaper.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.admin.wrappingpaper.service.WrappingPaperService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(SpringExtension.class)
class WrappingPaperAdminControllerTest {

	@Mock
	private WrappingPaperService wrappingPaperService;

	@InjectMocks
	private WrappingPaperAdminController wrappingPaperAdminController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(wrappingPaperAdminController).build();
	}

	@Test
	void testGetWrappingPapers() throws Exception {
		List<WrappingPaperResponse> wrappingPaperList = List.of(
			new WrappingPaperResponse(1L, "Wrapping Paper 1", "imageUrl1", BigDecimal.valueOf(100)),
			new WrappingPaperResponse(2L, "Wrapping Paper 2", "imageUrl2", BigDecimal.valueOf(200))
		);

		when(wrappingPaperService.getWrappingPapers(any())).thenReturn(
			new org.springframework.data.domain.PageImpl<>(wrappingPaperList)
		);

		mockMvc.perform(get("/admin/wrapping-paper")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/wrapping-paper/wrapping-paper"))
			.andExpect(model().attributeExists("pages"))
			.andExpect(model().attribute("pages", new org.springframework.data.domain.PageImpl<>(wrappingPaperList)));

		verify(wrappingPaperService).getWrappingPapers(any());
	}

	@Test
	void testRegisterWrappingPaper() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE,
			new byte[] {});
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), "Tag Created");

		when(wrappingPaperService.uploadImage(any(MultipartFile.class))).thenReturn("imageUrl");
		when(wrappingPaperService.registerWrappingPaper(any(WrappingPaperRequest.class))).thenReturn(responseMessage);

		mockMvc.perform(multipart("/admin/wrapping-paper")
				.file(imageFile)
				.param("title", "New Wrapping Paper")
				.param("wrappingPrice", "100"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("Tag Created"));

		verify(wrappingPaperService).uploadImage(any(MultipartFile.class));
		verify(wrappingPaperService).registerWrappingPaper(any(WrappingPaperRequest.class));
	}

	@Test
	void testRemoveWrappingPaper() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "Deleted Successfully");

		when(wrappingPaperService.deleteWrappingPaper(anyLong())).thenReturn(responseMessage);

		mockMvc.perform(delete("/admin/wrapping-paper/{wrapping-paper-id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Deleted Successfully"));

		verify(wrappingPaperService).deleteWrappingPaper(eq(1L));
	}
}

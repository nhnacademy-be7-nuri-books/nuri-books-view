package shop.nuribooks.view.admin.tag.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(SpringExtension.class)
class TagControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TagService tagService;

	@InjectMocks
	private TagController tagController;

	private ObjectMapper objectMapper;
	private TagRequest tagRequest;

	@BeforeEach
	void setUp() {
		tagRequest = new TagRequest("Test Tag");
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
	}

	@Test
	void testShowTagListWithoutPagination() throws Exception {
		List<TagResponse> tagList = List.of(new TagResponse(1L, "Tag 1"), new TagResponse(2L, "Tag 2"));

		when(tagService.getAllTags()).thenReturn(tagList);

		mockMvc.perform(get("/admin/tag/all"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/tag/tag"))
			.andExpect(model().attributeExists("tags"))
			.andExpect(model().attribute("tags", tagList));

		verify(tagService).getAllTags();
	}

	@Test
	void testRegisterTag() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), "태그 등록 성공");

		when(tagService.registerTag(any(TagRequest.class))).thenReturn(responseMessage);

		mockMvc.perform(post("/admin/tag").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tagRequest)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("태그 등록 성공"));
	}

	@Test
	void testUpdateTag() throws Exception {
		Long tagId = 1L;
		TagRequest updatedRequest = new TagRequest("Updated Tag");
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "태그 수정 성공");

		when(tagService.updateTag(eq(tagId), any(TagRequest.class))).thenReturn(responseMessage);

		mockMvc.perform(put("/admin/tag/{tag-id}", tagId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("태그 수정 성공"));
	}

	@Test
	void testDeleteTag() throws Exception {
		Long tagId = 1L;
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NO_CONTENT.value(), "태그 삭제 성공");

		when(tagService.deleteTag(anyLong())).thenReturn(responseMessage);

		mockMvc.perform(delete("/admin/tag/{tag-id}", tagId))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.message").value("태그 삭제 성공"));
	}
}

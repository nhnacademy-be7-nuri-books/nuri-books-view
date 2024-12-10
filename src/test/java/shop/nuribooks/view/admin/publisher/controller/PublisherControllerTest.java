package shop.nuribooks.view.admin.publisher.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.service.PublisherService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(SpringExtension.class)
class PublisherControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PublisherService publisherService;

	@InjectMocks
	private PublisherController publisherController;

	private ObjectMapper objectMapper;
	private PublisherRequest publisherRequest;

	@BeforeEach
	void setUp() {
		publisherRequest = new PublisherRequest("TestPublisher");
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(publisherController).build();
	}

	@Test
	void testRegisterPublisher() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), "출판사 등록 성공");

		when(publisherService.registerPublisher(any())).thenReturn(responseMessage);

		mockMvc.perform(post("/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(publisherRequest)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("출판사 등록 성공"));
	}

	@Test
	void testUpdatePublisher() throws Exception {
		Long publisherId = 1L;
		PublisherRequest updatedRequest = new PublisherRequest("Updated Publisher");
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "출판사 수정 성공");

		when(publisherService.updatePublisher(eq(publisherId), any(PublisherRequest.class)))
			.thenReturn(responseMessage);

		mockMvc.perform(put("/admin/publisher/{publisher-id}", publisherId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedRequest)))  // JSON 형식 요청 본문
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("출판사 수정 성공"));
	}

	@Test
	void testDeletePublisher() throws Exception {
		Long publisherId = 1L;
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NO_CONTENT.value(), "출판사 삭제 성공");

		when(publisherService.deletePublisher(any())).thenReturn(responseMessage);

		mockMvc.perform(delete("/admin/publisher/{publisher-id}", publisherId))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.message").value("출판사 삭제 성공"));
	}
}

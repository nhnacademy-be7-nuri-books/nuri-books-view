package shop.nuribooks.view.admin.contributor.controller;

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

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.service.ContributorService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(SpringExtension.class)
class ContributorControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ContributorService contributorService;

	@InjectMocks
	private ContributorController contributorController;

	private ObjectMapper objectMapper;
	private ContributorRequest contributorRequest;

	@BeforeEach
	void setUp() {
		contributorRequest = new ContributorRequest("Test Contributor");
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(contributorController).build();
	}

	@Test
	void testRegisterContributor() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), "기여자 등록 성공");

		when(contributorService.registerContributor(any())).thenReturn(responseMessage);

		mockMvc.perform(post("/admin/contributor").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(contributorRequest)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("기여자 등록 성공"));
	}

	@Test
	void testUpdateContributor() throws Exception {
		Long contributorId = 1L;
		ContributorRequest updatedRequest = new ContributorRequest("Updated Contributor");
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "기여자 수정 성공");

		when(contributorService.updateContributor(eq(contributorId), any(ContributorRequest.class))).thenReturn(
			responseMessage);

		mockMvc.perform(
				put("/admin/contributor/{contributor-id}", contributorId).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updatedRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("기여자 수정 성공"));
	}

	@Test
	void testDeleteContributor() throws Exception {
		Long contributorId = 1L;
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NO_CONTENT.value(), "기여자 삭제 성공");

		when(contributorService.deleteContributor(any())).thenReturn(responseMessage);

		mockMvc.perform(delete("/admin/contributor/{contributor-id}", contributorId))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.message").value("기여자 삭제 성공"));
	}
}

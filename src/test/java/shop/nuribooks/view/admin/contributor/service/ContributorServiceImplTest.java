package shop.nuribooks.view.admin.contributor.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.feign.ContributorServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class ContributorServiceImplTest {

	@Mock
	private ContributorServiceClient contributorServiceClient;

	@InjectMocks
	private ContributorServiceImpl contributorService;

	private ContributorRequest contributorRequest;
	private ContributorResponse contributorResponse;
	private ResponseMessage responseMessage;

	@BeforeEach
	void setUp() {
		contributorRequest = new ContributorRequest("John Doe");
		contributorResponse = new ContributorResponse(1L, "John Doe");
		responseMessage = new ResponseMessage(200, "Success");
	}

	@Test
	void testRegisterContributor() {
		when(contributorServiceClient.registerContributor(any(ContributorRequest.class)))
			.thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = contributorService.registerContributor(contributorRequest);

		assertEquals("Success", result.message());
		verify(contributorServiceClient).registerContributor(any(ContributorRequest.class));
	}

	@Test
	void testGetAllContributors() {
		List<ContributorResponse> contributors = List.of(contributorResponse);
		Page<ContributorResponse> contributorPage = new PageImpl<>(contributors, PageRequest.of(0, 10),
			contributors.size());

		when(contributorServiceClient.getAllContributors(anyInt(), anyInt()))
			.thenReturn(ResponseEntity.ok(contributorPage));

		Page<ContributorResponse> result = contributorService.getAllContributors(PageRequest.of(0, 10));

		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		assertEquals("John Doe", result.getContent().get(0).name());
		verify(contributorServiceClient).getAllContributors(0, 10);
	}

	@Test
	void testGetContributor() {
		when(contributorServiceClient.getContributor(anyLong()))
			.thenReturn(ResponseEntity.ok(contributorResponse));

		ContributorResponse result = contributorService.getContributor(1L);

		assertNotNull(result);
		assertEquals("John Doe", result.name());
		verify(contributorServiceClient).getContributor(1L);
	}

	@Test
	void testUpdateContributor() {
		ContributorRequest updatedRequest = new ContributorRequest("Jane Doe");
		ResponseMessage updatedResponseMessage = new ResponseMessage(200, "Updated Successfully");

		when(contributorServiceClient.updateContributor(anyLong(), any(ContributorRequest.class)))
			.thenReturn(ResponseEntity.ok(updatedResponseMessage));

		ResponseMessage result = contributorService.updateContributor(1L, updatedRequest);

		assertEquals("Updated Successfully", result.message());
		verify(contributorServiceClient).updateContributor(eq(1L), any(ContributorRequest.class));
	}

	@Test
	void testDeleteContributor() {
		when(contributorServiceClient.deleteContributor(anyLong()))
			.thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = contributorService.deleteContributor(1L);

		assertEquals("Success", result.message());
		verify(contributorServiceClient).deleteContributor(1L);
	}
}

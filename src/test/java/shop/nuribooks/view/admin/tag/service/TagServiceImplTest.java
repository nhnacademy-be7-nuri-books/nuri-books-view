package shop.nuribooks.view.admin.tag.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import feign.FeignException;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.feign.TagServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

	@InjectMocks
	private TagServiceImpl tagService;

	@Mock
	private TagServiceClient tagServiceClient;

	private TagRequest tagRequest;
	private TagResponse tagResponse;
	private ResponseMessage responseMessage;
	private ResponseMessage responseMessage1;

	@BeforeEach
	void setUp() {
		tagRequest = new TagRequest("Test Tag");
		tagResponse = new TagResponse(1L, "Test Tag");
		responseMessage = new ResponseMessage(200, "Success");
		responseMessage1 = new ResponseMessage(201, "Tag Created");
	}

	@Test
	void testRegisterTag() {
		when(tagServiceClient.registerTag(any(TagRequest.class))).thenReturn(
			ResponseEntity.status(HttpStatus.CREATED).body(responseMessage1));

		ResponseMessage response = tagService.registerTag(tagRequest);

		assertNotNull(response);
		assertEquals(201, response.statusCode());
		assertEquals("Tag Created", response.message());

		verify(tagServiceClient).registerTag(tagRequest);
	}

	@Test
	void testGetAllTags() {
		List<TagResponse> tagList = List.of(new TagResponse(1L, "Tag 1"), new TagResponse(2L, "Tag 2"));
		when(tagServiceClient.getAllTags()).thenReturn(ResponseEntity.ok(tagList));

		List<TagResponse> result = tagService.getAllTags();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Tag 1", result.get(0).name());

		verify(tagServiceClient).getAllTags();
	}

	@Test
	void testGetAllTags_emptyListOnFeignException() {
		when(tagServiceClient.getAllTags()).thenThrow(FeignException.class);

		List<TagResponse> result = tagService.getAllTags();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(tagServiceClient).getAllTags();
	}

	@Test
	void testGetAllTags_withPagination() {
		List<TagResponse> tagList = List.of(new TagResponse(1L, "Tag 1"), new TagResponse(2L, "Tag 2"));
		Page<TagResponse> tagPage = new PageImpl<>(tagList, PageRequest.of(0, 10), tagList.size());
		when(tagServiceClient.getAllTags(anyInt(), anyInt())).thenReturn(ResponseEntity.ok(tagPage));

		Page<TagResponse> result = tagService.getAllTags(PageRequest.of(0, 10));

		assertNotNull(result);
		assertEquals(2, result.getContent().size());
		assertEquals("Tag 1", result.getContent().get(0).name());

		verify(tagServiceClient).getAllTags(0, 10);
	}

	@Test
	void testGetTag() {
		when(tagServiceClient.getTag(anyLong())).thenReturn(ResponseEntity.ok(tagResponse));

		TagResponse result = tagService.getTag(1L);

		assertNotNull(result);
		assertEquals(1L, result.id());
		assertEquals("Test Tag", result.name());

		verify(tagServiceClient).getTag(1L);
	}

	@Test
	void testUpdateTag() {
		TagRequest updatedRequest = new TagRequest("Updated Tag");
		when(tagServiceClient.updateTag(anyLong(), any(TagRequest.class))).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = tagService.updateTag(1L, updatedRequest);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(tagServiceClient).updateTag(1L, updatedRequest);
	}

	@Test
	void testDeleteTag() {
		when(tagServiceClient.deleteTag(anyLong())).thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = tagService.deleteTag(1L);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(tagServiceClient).deleteTag(1L);
	}
}

package shop.nuribooks.view.admin.publisher.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.admin.publisher.PublisherServiceClient;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

	@Mock
	private PublisherServiceClient publisherServiceClient;

	@InjectMocks
	private PublisherServiceImpl publisherService;

	private PublisherRequest publisherRequest;
	private PublisherResponse publisherResponse;

	@BeforeEach
	void setUp() {
		publisherRequest = new PublisherRequest("test");
		publisherResponse = new PublisherResponse(1L, "test");
	}

	@Test
	void testRegisterPublisher() {
		ResponseMessage createResponse = new ResponseMessage(HttpStatus.CREATED.value(), "출판사 등록 성공");
		when(publisherServiceClient.registerPublisher(any(PublisherRequest.class)))
			.thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponse));

		ResponseMessage result = publisherService.registerPublisher(publisherRequest);

		verify(publisherServiceClient).registerPublisher(any(PublisherRequest.class));
		assert result.message().equals("출판사 등록 성공");
		assert result.statusCode() == 201;
	}

	@Test
	void testGetAllPublishers() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<PublisherResponse> page = new PageImpl<>(Collections.singletonList(publisherResponse));
		when(publisherServiceClient.getAllPublishers(anyInt(), anyInt()))
			.thenReturn(ResponseEntity.ok(page));

		Page<PublisherResponse> result = publisherService.getAllPublishers(pageable);

		verify(publisherServiceClient).getAllPublishers(anyInt(), anyInt());
		assert result.getTotalElements() == 1;
		assert result.getContent().get(0).name().equals("test");
	}

	@Test
	void testGetPublisher() {
		when(publisherServiceClient.getPublisherById(anyLong()))
			.thenReturn(ResponseEntity.ok(publisherResponse));

		PublisherResponse result = publisherService.getPublisher(1L);

		verify(publisherServiceClient).getPublisherById(anyLong());
		assert result.id() == 1L;
		assert result.name().equals("test");
	}

	@Test
	void testUpdatePublisher() {
		ResponseMessage updateResponse = new ResponseMessage(HttpStatus.OK.value(), "출판사 수정 성공");
		when(publisherServiceClient.updatePublisher(anyLong(), any(PublisherRequest.class)))
			.thenReturn(ResponseEntity.ok(updateResponse));

		ResponseMessage result = publisherService.updatePublisher(1L, publisherRequest);

		verify(publisherServiceClient).updatePublisher(anyLong(), any(PublisherRequest.class));
		assert result.message().equals("출판사 수정 성공");
		assert result.statusCode() == 200;
	}

	@Test
	void testDeletePublisher() {
		ResponseMessage deleteResponse = new ResponseMessage(HttpStatus.NO_CONTENT.value(), "출판사 삭제 성공");
		when(publisherServiceClient.deletePublisher(anyLong()))
			.thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteResponse));

		ResponseMessage result = publisherService.deletePublisher(1L);

		verify(publisherServiceClient).deletePublisher(anyLong());
		assert result.message().equals("출판사 삭제 성공");
		assert result.statusCode() == 204;
	}
}
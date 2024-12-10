package shop.nuribooks.view.admin.wrappingpaper.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.feign.WrappingPaperServiceFeignClient;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@ExtendWith(MockitoExtension.class)
class WrappingPaperServiceImplTest {

	@InjectMocks
	private WrappingPaperServiceImpl wrappingPaperService;

	@Mock
	private WrappingPaperServiceFeignClient wrappingPaperServiceFeignClient;

	@Mock
	private BookServiceClient bookServiceClient;

	private WrappingPaperRequest wrappingPaperRequest;
	private ResponseMessage responseMessage;

	@BeforeEach
	void setUp() {
		wrappingPaperRequest = new WrappingPaperRequest("Test Title", "imageUrl", BigDecimal.valueOf(100));
		responseMessage = new ResponseMessage(200, "Success");
	}

	@Test
	void testRegisterWrappingPaper() {
		when(wrappingPaperServiceFeignClient.registerWrappingPaper(any(WrappingPaperRequest.class))).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = wrappingPaperService.registerWrappingPaper(wrappingPaperRequest);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(wrappingPaperServiceFeignClient).registerWrappingPaper(wrappingPaperRequest);
	}

	@Test
	void testUpdateWrappingPaper() {
		when(wrappingPaperServiceFeignClient.updateWrappingPaper(any(WrappingPaperRequest.class), eq(1L))).thenReturn(
			ResponseEntity.ok(responseMessage));

		ResponseMessage result = wrappingPaperService.updateWrappingPaper(1L, wrappingPaperRequest);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(wrappingPaperServiceFeignClient).updateWrappingPaper(eq(wrappingPaperRequest), eq(1L));
	}

	@Test
	void testDeleteWrappingPaper() {
		when(wrappingPaperServiceFeignClient.removeWrappingPaper(1L)).thenReturn(ResponseEntity.ok(responseMessage));

		ResponseMessage result = wrappingPaperService.deleteWrappingPaper(1L);

		assertNotNull(result);
		assertEquals(200, result.statusCode());
		assertEquals("Success", result.message());

		verify(wrappingPaperServiceFeignClient).removeWrappingPaper(1L);
	}

	@Test
	void testUploadImage() {
		MultipartFile file = mock(MultipartFile.class);
		when(bookServiceClient.uploadImage(any(MultipartFile.class))).thenReturn("imageUrl");

		String result = wrappingPaperService.uploadImage(file);

		assertNotNull(result);
		assertEquals("imageUrl", result);

		verify(bookServiceClient).uploadImage(any(MultipartFile.class));
	}
}

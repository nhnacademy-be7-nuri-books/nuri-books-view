package shop.nuribooks.view.admin.publisher;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "publishers", url = "http://localhost:8080")
public interface PublisherServiceClient {
	@PostMapping("/api/books/publishers")
	ResponseEntity<ResponseMessage> registerPublisher(@RequestBody PublisherRequest request);

	@GetMapping("/api/books/publishers")
	ResponseEntity<Page<PublisherResponse>> getAllPublishers(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	@PutMapping("/api/books/publishers/{publisher-id}")
		//수정
	ResponseEntity<ResponseMessage> updatePublisher(@PathVariable(name = "publisher-id") Long publisherId,
		@RequestBody PublisherRequest request);

	@GetMapping("/api/books/publishers/{publisher-id}")
	ResponseEntity<PublisherResponse> getPublisherById(@PathVariable(name = "publisher-id") Long publisherId);

	@DeleteMapping("/api/books/publishers/{publisher-id}")
	ResponseEntity<ResponseMessage> deletePublisher(@PathVariable(name = "publisher-id") Long publisherId);

}

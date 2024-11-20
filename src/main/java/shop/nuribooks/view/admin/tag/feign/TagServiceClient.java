package shop.nuribooks.view.admin.tag.feign;

import java.util.List;

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

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "tag", url = "http://localhost:8080")
public interface TagServiceClient {
	@GetMapping("/api/books/tags/all")
	ResponseEntity<List<TagResponse>> getAllTags();

	@GetMapping("/api/books/tags")
	ResponseEntity<Page<TagResponse>> getAllTags(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	@PostMapping("/api/books/tags")
	ResponseEntity<ResponseMessage> registerTag(@Valid @RequestBody TagRequest request);

	@GetMapping("/api/books/tags/{tagId}")
	ResponseEntity<TagResponse> getTag(@Valid @PathVariable Long tagId);

	@PutMapping("/api/books/tags/{tagId}")
	ResponseEntity<ResponseMessage> updateTag(@Valid @PathVariable Long tagId, @RequestBody TagRequest request);

	@DeleteMapping("/api/books/tags/{tagId}")
	ResponseEntity<ResponseMessage> deleteTag(@Valid @PathVariable Long tagId);
}
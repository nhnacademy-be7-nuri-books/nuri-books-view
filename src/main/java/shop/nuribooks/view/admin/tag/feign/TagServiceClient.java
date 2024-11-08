package shop.nuribooks.view.admin.tag.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import shop.nuribooks.view.admin.tag.dto.TagResponse;

@FeignClient(name = "tag", url = "http://localhost:8080")
public interface TagServiceClient {
	@GetMapping("/api/books/tags")
	ResponseEntity<List<TagResponse>> getAllTags();
}

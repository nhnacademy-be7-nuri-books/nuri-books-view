package shop.nuribooks.view.admin.tag.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;

import java.util.List;

@FeignClient(name = "tag", url = "http://localhost:8080")
public interface TagServiceClient {
    @GetMapping("/api/books/tags/all")
    ResponseEntity<List<TagResponse>> getAllTags();

    @GetMapping("/api/books/tags")
    ResponseEntity<Page<TagResponse>> getAllTags(
        @RequestParam(value = "page") int page,
        @RequestParam(value = "size") int size);

    @PostMapping("/api/books/tags")
    ResponseEntity<TagResponse> registerTag(@Valid @RequestBody TagRequest request);

    @GetMapping("/api/books/tags/{tagId}")
    ResponseEntity<TagResponse> getTag(@Valid @PathVariable Long tagId);

    @PutMapping("/api/books/tags/{tagId}")
    ResponseEntity<TagResponse> updateTag(@Valid @PathVariable Long tagId, @RequestBody TagRequest request);

    @DeleteMapping("/api/books/tags/{tagId}")
    ResponseEntity<HttpStatus> deleteTag(@Valid @PathVariable Long tagId);
}
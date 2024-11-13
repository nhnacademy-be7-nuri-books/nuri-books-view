package shop.nuribooks.view.admin.publisher;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;

import java.awt.print.Pageable;
import java.util.List;

@FeignClient(name = "books", url = "http://localhost:8080")
public interface PublisherServiceClient {
    @PostMapping("/api/publishers")
    ResponseEntity<PublisherResponse> registerPublisher(@RequestBody PublisherRequest request);

    @GetMapping("/api/publishers")
    ResponseEntity<Page<PublisherResponse>> getAllPublishers(
        @RequestParam(value = "page") int page,
        @RequestParam(value = "size") int size);

    @PutMapping("/api/publishers/{publisherId}") //수정
    ResponseEntity<PublisherResponse> updatePublisher(@PathVariable Long publisherId, @RequestBody PublisherRequest request);

    @GetMapping("/api/publishers/{publisherId}")
    ResponseEntity<PublisherResponse> getPublisherById(@PathVariable Long publisherId);

    @DeleteMapping("/api/publishers/{publisherId}")
    ResponseEntity<HttpStatus> deletePublisher(@PathVariable Long publisherId);

}

package shop.nuribooks.view.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.common.dto.PagedResponse;

@FeignClient(name = "book", url = "http://localhost:8080")
public interface BookServiceClient {
	@GetMapping("/api/books")
	PagedResponse<AdminBookListResponse> getBooks(@RequestParam("page") int page, @RequestParam("size") int size);
}

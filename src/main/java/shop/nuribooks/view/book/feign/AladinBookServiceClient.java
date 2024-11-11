package shop.nuribooks.view.book.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.book.dto.AladinBookListItemResponse;

@FeignClient(name = "aladinBook", url = "http://localhost:8080")
public interface AladinBookServiceClient {
	@GetMapping("/api/books/aladin")
	ResponseEntity<List<AladinBookListItemResponse>> getAladinBookList(@RequestParam("queryType") String queryType,
																@RequestParam("searchTarget") String searchTarget,
																@RequestParam("maxResults") int maxResults);

	@GetMapping("/api/books/aladin/{isbn}")
	ResponseEntity<AladinBookListItemResponse> getBookByIsbnWithAladin(@PathVariable String isbn);
}

package shop.nuribooks.view.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "aladinBook", url = "http://localhost:8083")
public interface AladinBookServiceClient {
	@GetMapping("api/books/aladin")
	ResponseEntity<String> getAladinBookList();
}

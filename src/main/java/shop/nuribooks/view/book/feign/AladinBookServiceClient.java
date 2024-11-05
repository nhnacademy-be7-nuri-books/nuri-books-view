package shop.nuribooks.view.book.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "aladinBook", url = "http://localhost:8083")
public interface AladinBookServiceClient {
	@GetMapping("/api/books/aladin")
	ResponseEntity<List<AladinBookListItemResponse>> getAladinBookList();

	@GetMapping("/api/books/aladin/{isbn}")
	ResponseEntity<AladinBookListItemResponse> getBookByIsbn(@PathVariable String isbn);

	@PostMapping("api/books/aladin/save")
	ResponseEntity<ResponseMessage> saveAladinBook(@Valid @RequestBody AladinBookSaveRequest saveRequest);
}

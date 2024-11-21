package shop.nuribooks.view.admin.wrappingpaper.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "wrapping-paper", url = "http://localhost:8080/admin/api/wrapping-papers")
public interface WrappingPaperServiceFeignClient {
	@GetMapping
	ResponseEntity<Page<WrappingPaperResponse>> getWrappingPapers(Pageable pageable);

	@PostMapping
	ResponseEntity<ResponseMessage> registerWrappingPaper(
		@Valid @RequestBody WrappingPaperRequest wrappingPaperRequest);

	@PutMapping("/{wrapping-paper-id}")
	ResponseEntity<ResponseMessage> updateWrappingPaper(
		@Valid @RequestBody WrappingPaperRequest wrappingPaperRequest,
		@PathVariable("wrapping-paper-id") Long id
	);

	@DeleteMapping("/{wrapping-paper-id}")
	ResponseEntity<ResponseMessage> removeWrappingPaper(
		@PathVariable("wrapping-paper-id") Long id);
}

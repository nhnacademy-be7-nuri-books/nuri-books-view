package shop.nuribooks.view.admin.contributor.feign;

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
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "contributorServiceClient", url = "http://localhost:8080")
public interface ContributorServiceClient {
	@PostMapping("/api/contributors")
	ResponseEntity<ResponseMessage> registerContributor(@Valid @RequestBody ContributorRequest request);

	@GetMapping("/api/contributors/{contributor-id}")
	ResponseEntity<ContributorResponse> getContributor(@PathVariable(name = "contributor-id") Long contributorId);

	@GetMapping("/api/contributors")
	ResponseEntity<Page<ContributorResponse>> getAllContributors(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size);

	@PutMapping("/api/contributors/{contributor-id}")
	ResponseEntity<ResponseMessage> updateContributor(
		@PathVariable(name = "contributor-id") Long contributorId, @Valid @RequestBody ContributorRequest request);

	@DeleteMapping("/api/contributors/{contributor-id}")
	ResponseEntity<ResponseMessage> deleteContributor(@PathVariable(name = "contributor-id") Long contributorId);
}

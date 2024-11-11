package shop.nuribooks.view.admin.contributor.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;

import java.util.List;

@FeignClient(name = "contributorServiceClient", url = "http://localhost:8080")
public interface ContributorServiceClient {
    @PostMapping("/api/contributors")
    ResponseEntity<ContributorResponse> registerContributor(@Valid @RequestBody ContributorRequest request);

    @GetMapping("/api/contributors/{contributorId}")
    ResponseEntity<ContributorResponse> getContributor(@PathVariable Long contributorId);

    @GetMapping("/api/contributors")
    ResponseEntity<List<ContributorResponse>> getAllContributors();

    @PutMapping("/api/contributors/{contributorId}")
    ResponseEntity<ContributorResponse> updateContributor(
            @PathVariable Long contributorId, @Valid @RequestBody ContributorRequest request);

    @DeleteMapping("/api/contributors/{contributorId}")
    ResponseEntity<HttpStatus> deleteContributor(@PathVariable Long contributorId);
}

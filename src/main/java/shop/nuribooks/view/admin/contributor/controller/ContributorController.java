package shop.nuribooks.view.admin.contributor.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.service.ContributorService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/contributor")
public class ContributorController {

    private final ContributorService contributorService;

    @GetMapping
    public String showRegisterContributorForm(Model model) {
        List<ContributorResponse> contributors = contributorService.getAllContributors();
        model.addAttribute("contributors", contributors);
        return "admin/contributor";
    }

    //기여자 등록
    @PostMapping
    public ResponseEntity<Map<String, String>> registerContributor(ContributorRequest contributorRequest) {
        try {
            contributorService.registerContributor(contributorRequest);
            return ResponseEntity.ok(Map.of(contributorRequest.name(), "등록 성공"));
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", ex.getMessage()));
        }
    }

    // 기여자 수정 처리
    @PutMapping("/{contributor-id}")
    public ResponseEntity<Map<String, String>> updateContributor(@PathVariable(name = "contributor-id") Long id, @RequestBody ContributorRequest contributorRequest) {
        try {
            contributorService.updateContributor(id, contributorRequest);
            return ResponseEntity.ok(Map.of(contributorRequest.name(), "수정 성공"));
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", ex.getMessage()));
        }
    }

    //기여자 삭제
    @DeleteMapping("/{contributor-id}")
    public ResponseEntity<Map<String, String>> deleteContributor(@PathVariable(name = "contributor-id") Long contributorId) {
        try {
            contributorService.deleteContributor(contributorId);
            return ResponseEntity.ok(Map.of("data", "삭제 성공"));
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", ex.getMessage()));
        }
    }
}

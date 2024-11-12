package shop.nuribooks.view.admin.publisher.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.admin.publisher.service.PublisherService;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/publisher")
public class PublisherController {
    private final PublisherService publisherService;

    @Value("${error.message-key}")
    private String errorMessageKey;

    @Value("${success.message-key}")
    private String successMessageKey;

    //출판사 페이지
    @GetMapping
    public String showRegisterPublisherForm(Model model) {
        List<PublisherResponse> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        return "admin/publisher";
    }

    //출판사 등록 버튼
    @PostMapping
    public ResponseEntity<Map<String, String>> registerPublisher(PublisherRequest publisherRequest) {
        try {
            publisherService.registerPublisher(publisherRequest);
            return ResponseEntity.ok(Map.of(publisherRequest.name(), "등록 성공"));
        }catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(HttpStatus.CONFLICT.toString(), ex.getMessage()));
        }
    }

    // 출판사 수정 처리
    @PostMapping("/edit/{id}")
    public ResponseEntity<Map<String, String>> updatePublisher(@PathVariable Long id, @RequestBody PublisherRequest publisherRequest) {
        try {
            publisherService.updatePublisher(id, publisherRequest);
            return ResponseEntity.ok(Map.of(publisherRequest.name(), "수정 성공"));
        }catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", "error", "message", ex.getMessage()));
            }
    }

    //출판사 삭제
    @PostMapping("/delete/{publisherId}")
    public ResponseEntity<Map<String, String>> deletePublisher(@PathVariable Long publisherId) {
        try {
            publisherService.deletePublisher(publisherId);
            return ResponseEntity.ok(Map.of("data", "삭제 성공"));
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "error", "message", ex.getMessage()));
        }
    }
}

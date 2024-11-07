package shop.nuribooks.view.admin.publisher.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.admin.publisher.service.PublisherService;
import shop.nuribooks.view.common.util.ExceptionUtil;

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

    //관리자 페이지 -> 출판사 페이지로 들어오면
    @GetMapping
    public String showRegisterPublisherForm(Model model) {
        List<PublisherResponse> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        return "/admin/publisher";
    }

    //출판사 등록 버튼
    @PostMapping
    public String registerPublisher(PublisherRequest publisherRequest) {
        publisherService.registerPublisher(publisherRequest);
        return "redirect:/admin";
    }

    // 출판사 수정 처리
    @PostMapping("/edit/{id}")
    public ResponseEntity<String> updatePublisher(@PathVariable Long id, @RequestBody PublisherRequest publisherRequest) {
        publisherService.updatePublisher(id, publisherRequest);
        return ResponseEntity.ok("Publisher updated successfully.");
    }



    //출판사 삭제
    @PostMapping("/delete/{publisherId}")
    public ResponseEntity<String> deletePublisher(@PathVariable Long publisherId) {
        try {
            publisherService.deletePublisher(publisherId);
            return ResponseEntity.ok("Publisher deleted successfully.");
        } catch (FeignException ex) {
            String errorMessage = ExceptionUtil.handleFeignException(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}

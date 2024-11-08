package shop.nuribooks.view.admin.tag.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class TagController {

    private final TagService tagService;

    @Value("${error.message-key}")
    private String errorMessageKey;

    @Value("${success.message-key}")
    private String successMessageKey;

    @GetMapping
    public String showRegisterTagForm(Model model) {
        List<TagResponse> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);
        return "/admin/tag";
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerTag(TagRequest request) {
        try {
            tagService.registerTag(request);
            return ResponseEntity.ok(Map.of(request.name(), "등록 성공"));
        }catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(HttpStatus.CONFLICT.toString(), ex.getMessage()));
        }
    }
    
}

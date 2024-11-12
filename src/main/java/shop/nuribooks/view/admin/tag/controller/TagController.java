package shop.nuribooks.view.admin.tag.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String showTagList(Model model) {
        List<TagResponse> tags = tagService.getAllTags();
        model.addAttribute("tags", tags);
        return "admin/tag/tag-list";
    }

    @GetMapping("/register")
    public String showTagRegisterPage() {
        return "admin/tag/tag-register";
    }

    @PostMapping
    public String registerTag(TagRequest tagRequest) {
        tagService.registerTag(tagRequest);
        return "redirect:/admin/tag";
    }

    @GetMapping("/{tag-id}")
    public String showEditTagPage(@PathVariable("tag-id") Long id, Model model) {
        TagResponse tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return "admin/tag/tag-edit";
    }

    @PutMapping("/{tag-id}")
    public String updateTag(@PathVariable("tag-id") Long id, TagRequest tagRequest) {
        tagService.updateTag(id, tagRequest);
        return "redirect:/admin/tag";
    }

    @DeleteMapping("/{tag-id}")
    public String deleteTag(@PathVariable("tag-id") Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tag";
    }
}

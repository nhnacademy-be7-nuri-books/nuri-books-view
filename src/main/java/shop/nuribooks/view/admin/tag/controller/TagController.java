package shop.nuribooks.view.admin.tag.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class TagController {

	private final TagService tagService;

	@GetMapping("/all")
	public String showTagList(Model model) {
		List<TagResponse> tags = tagService.getAllTags();
		model.addAttribute("tags", tags);
		return "admin/tag/tag";
	}

	@GetMapping
	public String showTagList(@PageableDefault Pageable pageable, Model model) {
		Page<TagResponse> tags = tagService.getAllTags(pageable);
		model.addAttribute("pages", tags);
		return "admin/tag/tag";
	}

	// @GetMapping("/register")
	// public String showTagRegisterPage() {
	// 	return "admin/tag/tag-register";
	// }

	@PostMapping
	public ResponseEntity<ResponseMessage> registerTag(TagRequest tagRequest) {
		ResponseMessage message = tagService.registerTag(tagRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	// @GetMapping("/{tag-id}")
	// public String showEditTagPage(@PathVariable("tag-id") Long id, Model model) {
	// 	TagResponse tag = tagService.getTag(id);
	// 	model.addAttribute("tag", tag);
	// 	return "admin/tag/tag-edit";
	// }

	@PutMapping("/{tag-id}")
	public ResponseEntity<ResponseMessage> updateTag(@PathVariable("tag-id") Long id, TagRequest tagRequest) {
		ResponseMessage message = tagService.updateTag(id, tagRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/{tag-id}")
	public ResponseEntity<ResponseMessage> deleteTag(@PathVariable("tag-id") Long id) {
		ResponseMessage message = tagService.deleteTag(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}
}

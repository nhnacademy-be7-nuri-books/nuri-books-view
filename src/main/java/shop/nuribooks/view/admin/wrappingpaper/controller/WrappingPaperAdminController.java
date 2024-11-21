package shop.nuribooks.view.admin.wrappingpaper.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.admin.wrappingpaper.service.WrappingPaperService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@RequestMapping("/admin/wrapping-paper")
@Controller
public class WrappingPaperAdminController {
	private final WrappingPaperService wrappingPaperService;

	@GetMapping
	public String getWrappingPapers(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "5") int size,
		Model model
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<WrappingPaperResponse> pageResponse = wrappingPaperService.getWrappingPapers(pageable);
		model.addAttribute("pages", pageResponse);
		return "admin/wrapping-paper/wrapping-paper";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerWrappingPaper(
		@Valid @ModelAttribute WrappingPaperRequest wrappingPaperRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(wrappingPaperService.registerWrappingPaper(wrappingPaperRequest));
	}

	@PutMapping("/{wrapping-paper-id}")
	public ResponseEntity<ResponseMessage> updateWrappingPaper(
		@Valid @ModelAttribute WrappingPaperRequest wrappingPaperRequest,
		@PathVariable("wrapping-paper-id") Long id
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(wrappingPaperService.updateWrappingPaper(id, wrappingPaperRequest));
	}

	@DeleteMapping("/{wrapping-paper-id}")
	public ResponseEntity<ResponseMessage> removeWrappingPaper(
		@PathVariable("wrapping-paper-id") Long id
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(wrappingPaperService.deleteWrappingPaper(id));
	}
}

package shop.nuribooks.view.admin.wrappingpaper.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.admin.wrappingpaper.service.WrappingPaperService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/wrapping-paper")
@Controller
public class WrappingPaperAdminController {
	private final WrappingPaperService wrappingPaperService;

	@Operation(summary = "포장지 조회", description = "포장지를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
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

	@Operation(summary = "포장지 등록", description = "포장지를 등록합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "조회 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PostMapping
	public ResponseEntity<ResponseMessage> registerWrappingPaper(
		@RequestParam("title") String title,
		@RequestParam("wrappingPrice") BigDecimal wrappingPrice,
		@RequestParam("imageFile") MultipartFile imageFile
	) {
		String imageUrl = wrappingPaperService.uploadImage(imageFile);
		WrappingPaperRequest wrappingPaperRequest = new WrappingPaperRequest(title, imageUrl, wrappingPrice);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(wrappingPaperService.registerWrappingPaper(wrappingPaperRequest));
	}

	@Operation(summary = "포장지 수정", description = "포장지를 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@PutMapping("/{wrapping-paper-id}")
	public ResponseEntity<ResponseMessage> updateWrappingPaper(
		@RequestParam("title") String title,
		@RequestParam("wrappingPrice") BigDecimal wrappingPrice,
		@RequestParam("imageFile") MultipartFile imageFile,
		@PathVariable("wrapping-paper-id") Long id
	) {
		String imageUrl = wrappingPaperService.uploadImage(imageFile);
		WrappingPaperRequest wrappingPaperRequest = new WrappingPaperRequest(title, imageUrl, wrappingPrice);
		return ResponseEntity.status(HttpStatus.OK)
			.body(wrappingPaperService.updateWrappingPaper(id, wrappingPaperRequest));
	}

	@Operation(summary = "포장지 삭제", description = "포장지를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
	})
	@DeleteMapping("/{wrapping-paper-id}")
	public ResponseEntity<ResponseMessage> removeWrappingPaper(
		@PathVariable("wrapping-paper-id") Long id
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(wrappingPaperService.deleteWrappingPaper(id));
	}
}

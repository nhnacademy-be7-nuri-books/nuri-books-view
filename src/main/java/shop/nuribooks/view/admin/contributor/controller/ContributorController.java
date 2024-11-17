package shop.nuribooks.view.admin.contributor.controller;

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
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.service.ContributorService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/contributor")
public class ContributorController {

	private final ContributorService contributorService;

	@GetMapping
	public String showContributorList(@PageableDefault Pageable pageable, Model model) {
		Page<ContributorResponse> contributors = contributorService.getAllContributors(pageable);
		model.addAttribute("pages", contributors);
		return "admin/contributor/contributor";
	}

	// @GetMapping("/register")
	// public String showContributorRegisterPage() {
	// 	return "admin/contributor/contributor-register";
	// }

	@PostMapping
	public ResponseEntity<ResponseMessage> registerContributor(ContributorRequest contributorRequest) {
		ResponseMessage message = contributorService.registerContributor(contributorRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	// @GetMapping("/{contributor-id}")
	// public ResponseEntity<ResponseMessage> showEditContributorPage(@PathVariable("contributor-id") Long id, Model model) {
	// 	ResponseMessage message = contributorService.getContributor(id);
	// 	return ResponseEntity.status(HttpStatus.CREATED ).body(message);
	// }

	@PutMapping("/{contributor-id}")
	public ResponseEntity<ResponseMessage> updateContributor(@PathVariable("contributor-id") Long id,
		ContributorRequest contributorRequest) {
		ResponseMessage message = contributorService.updateContributor(id, contributorRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/{contributor-id}")
	public ResponseEntity<ResponseMessage> deleteContributor(@PathVariable("contributor-id") Long id) {
		ResponseMessage message = contributorService.deleteContributor(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}
}

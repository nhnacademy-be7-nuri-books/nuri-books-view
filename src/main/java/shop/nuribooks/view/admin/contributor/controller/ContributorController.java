package shop.nuribooks.view.admin.contributor.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/contributor")
public class ContributorController {

	private final ContributorService contributorService;

	@GetMapping
	public String showContributorList(@PageableDefault Pageable pageable, Model model) {
		Page<ContributorResponse> contributors = contributorService.getAllContributors(pageable);
		model.addAttribute("pages", contributors);
		return "admin/contributor/contributor-list";
	}

	@GetMapping("/register")
	public String showContributorRegisterPage() {
		return "admin/contributor/contributor-register";
	}

	@PostMapping
	public String registerContributor(ContributorRequest contributorRequest) {
		contributorService.registerContributor(contributorRequest);
		return "redirect:/admin/contributor";
	}

	@GetMapping("/{contributor-id}")
	public String showEditContributorPage(@PathVariable("contributor-id") Long id, Model model) {
		ContributorResponse contributor = contributorService.getContributor(id);
		model.addAttribute("contributor", contributor);
		return "admin/contributor/contributor-edit";
	}

	@PutMapping("/{contributor-id}")
	public String updateContributor(@PathVariable("contributor-id") Long id, ContributorRequest contributorRequest) {
		contributorService.updateContributor(id, contributorRequest);
		return "redirect:/admin/contributor";
	}

	@DeleteMapping("/{contributor-id}")
	public String deleteContributor(@PathVariable("contributor-id") Long id) {
		contributorService.deleteContributor(id);
		return "redirect:/admin/contributor";
	}
}

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
    public String showContributorList(Model model) {
        List<ContributorResponse> contributors = contributorService.getAllContributors();
        model.addAttribute("contributors", contributors);
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

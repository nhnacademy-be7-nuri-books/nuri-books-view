package shop.nuribooks.view.admin.grade.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.grade.dto.GradeDetailsResponse;
import shop.nuribooks.view.admin.grade.dto.GradeListResponse;
import shop.nuribooks.view.admin.grade.dto.GradeRegisterRequest;
import shop.nuribooks.view.admin.grade.dto.GradeUpdateRequest;
import shop.nuribooks.view.admin.grade.service.AdminGradeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/grade")
public class AdminGradeController {

	private final AdminGradeService adminGradeService;

	@GetMapping
	public String showGradeList(@ModelAttribute("message") String message, Model model) {

		if (message != null) {
			model.addAttribute("message", message);
		}

		List<GradeListResponse> grades = adminGradeService.getAllGrades();
		model.addAttribute("grades", grades);

		return "/admin/grade/grade-list";
	}

	@GetMapping("/form")
	public String showGradeRegisterForm() {

		return "/admin/grade/grade-register";
	}

	@PostMapping
	public String GradeRegister(@Valid @ModelAttribute GradeRegisterRequest request) {

		adminGradeService.registerGrade(request);

		return "redirect:/admin/grade";
	}

	@GetMapping("/{name}")
	public String gradeUpdateForm(@PathVariable String name, Model model) {

		GradeDetailsResponse grade = adminGradeService.getGradeDetails(name);
		model.addAttribute("grade", grade);

		return "/admin/grade/grade-edit";
	}

	@PutMapping("/{name}")
	public String gradeUpdate(@PathVariable String name, @Valid @ModelAttribute GradeUpdateRequest request) {

		adminGradeService.updateGrade(name, request);

		return "redirect:/admin/grade";
	}

	@DeleteMapping("/{name}")
	public String gradeDelete(@PathVariable String name, RedirectAttributes redirectAttributes) {

		String resultMessage = adminGradeService.deleteGrade(name);
		redirectAttributes.addFlashAttribute("message", resultMessage);

		return "redirect:/admin/grade";
	}
}

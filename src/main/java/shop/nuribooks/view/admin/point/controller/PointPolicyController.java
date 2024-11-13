package shop.nuribooks.view.admin.point.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.point.dto.PointPolicyRequest;
import shop.nuribooks.view.admin.point.dto.PointPolicyResponse;
import shop.nuribooks.view.admin.point.enums.PolicyType;
import shop.nuribooks.view.admin.point.service.PointPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/point-policy")
public class PointPolicyController {
	private final PointPolicyService pointPolicyService;

	@GetMapping
	public String getPointPolicy(Model model, @PageableDefault Pageable pageable){
		Page<PointPolicyResponse> policies = this.pointPolicyService.getPointPolicies(pageable);
		model.addAttribute("pages", policies);
		model.addAttribute("policyTypes", PolicyType.values());
		return "admin/point_policy";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerPointPolicy(@Valid @ModelAttribute PointPolicyRequest pointPolicyRequest){
		ResponseMessage message = this.pointPolicyService.registerPointPolicy(pointPolicyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}
}

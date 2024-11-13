package shop.nuribooks.view.admin.point.controller;

import java.math.BigDecimal;
import java.security.Policy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
		return "admin/point-policy/point_policy";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerPointPolicy(@Valid @ModelAttribute PointPolicyRequest pointPolicyRequest){
		if(pointPolicyRequest.amount().compareTo(BigDecimal.valueOf(100)) > 0 && pointPolicyRequest.policyType().equals(PolicyType.RATED)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(),"100%를 넘을 수 없습니다."));
		}
		ResponseMessage message = this.pointPolicyService.registerPointPolicy(pointPolicyRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{point-policy-id}")
	public ResponseEntity<ResponseMessage> updatePointPolicy(@PathVariable("point-policy-id") long id, @Valid @ModelAttribute PointPolicyRequest pointPolicyRequest){
		if(pointPolicyRequest.amount().compareTo(BigDecimal.valueOf(100)) > 0 && pointPolicyRequest.policyType().equals(PolicyType.RATED)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(),"100%를 넘을 수 없습니다."));
		}
		ResponseMessage message = this.pointPolicyService.updatePointPolicy(id, pointPolicyRequest);
		return ResponseEntity.status(HttpStatus.OK ).body(message);
	}
	
	@DeleteMapping("/{point-policy-id}")
	public ResponseEntity<ResponseMessage> deletePointPolicy(@PathVariable("point-policy-id") long id){
		ResponseMessage message = this.pointPolicyService.deletePointPolicy(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}
}

package shop.nuribooks.view.admin.point.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.point.dto.PointPolicyRequest;
import shop.nuribooks.view.admin.point.dto.PointPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "point-policy", url = "http://localhost:8080/admin/api/point-policies")
public interface PointPolicyServiceClient {
	@GetMapping
	ResponseEntity<Page<PointPolicyResponse>> getPointPolicies(Pageable pageable);

	@PostMapping
	ResponseEntity<ResponseMessage> registerPointPolicy(@Valid @RequestBody PointPolicyRequest pointPolicyRequest);

	@PutMapping("/{point-policy-id}")
	ResponseEntity<ResponseMessage> updatePointPolicy(@PathVariable("point-policy-id") long id, @Valid @RequestBody PointPolicyRequest pointPolicyRequest);

	@DeleteMapping("/{point-policy-id}")
	ResponseEntity<ResponseMessage> deletePointPolicy(@PathVariable("point-policy-id") long id);
}

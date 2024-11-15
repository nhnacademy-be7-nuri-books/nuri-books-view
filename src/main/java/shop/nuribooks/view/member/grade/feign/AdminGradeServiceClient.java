package shop.nuribooks.view.member.grade.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.grade.dto.GradeDetailsResponse;
import shop.nuribooks.view.member.grade.dto.GradeListResponse;
import shop.nuribooks.view.member.grade.dto.GradeRegisterRequest;
import shop.nuribooks.view.member.grade.dto.GradeUpdateRequest;

@FeignClient(name = "grade", url = "http://localhost:8080")
public interface AdminGradeServiceClient {

	@GetMapping("/api/members/grades")
	ResponseEntity<List<GradeListResponse>> getGradeList();

	@PostMapping("/api/members/grades")
	ResponseEntity<ResponseMessage> registerGrade(@RequestBody GradeRegisterRequest request);

	@GetMapping("/api/members/grades/{name}")
	ResponseEntity<GradeDetailsResponse> getGradeDetails(@PathVariable String name);

	@PutMapping("/api/members/grades/{name}")
	ResponseEntity<ResponseMessage> updateGrade(@PathVariable String name, @RequestBody GradeUpdateRequest request);

	@DeleteMapping("/api/members/grades/{name}")
	ResponseEntity<ResponseMessage> deleteGrade(@PathVariable String name);
}

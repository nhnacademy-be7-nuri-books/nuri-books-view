package shop.nuribooks.view.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import shop.nuribooks.view.dto.common.ResponseMessage;
import shop.nuribooks.view.dto.member.request.MemberCreateRequest;

@FeignClient(name = "books")
public interface MemberServiceClient {

	@PostMapping("/api/member")
	ResponseEntity<ResponseMessage> registerUser(@Valid MemberCreateRequest userRequest);
}

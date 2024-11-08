package shop.nuribooks.view.member.address.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;

@FeignClient(name = "address", url = "http://localhost:8080")
public interface AddressClient {

    @PostMapping("/api/member/address")
    ResponseEntity<ResponseMessage> registerAddress(@RequestBody AddressRegisterRequest request);
}

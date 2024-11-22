package shop.nuribooks.view.member.address.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.dto.request.AddressUpdateRequest;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;

@FeignClient(name = "address", url = "http://localhost:8080")
public interface AddressClient {

	@PostMapping("/api/members/addresses")
	ResponseEntity<Void> registerAddress(@RequestBody AddressRegisterRequest request);

	@GetMapping("/api/members/me/addresses")
	ResponseEntity<List<AddressResponse>> getAddressList();

	@PutMapping("/api/members/addresses/{address-id}")
	ResponseEntity<Void> updateAddress(@PathVariable(name = "address-id") Long addressId, @RequestBody
	AddressUpdateRequest request);

	@DeleteMapping("/api/members/addresses/{address-id}")
	ResponseEntity<Void> deleteAddress(@PathVariable(name = "address-id") Long addressId);

}

package shop.nuribooks.view.member.address.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.dto.request.AddressUpdateRequest;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;
import shop.nuribooks.view.member.address.service.AddressClientService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AddressController {

	private final AddressClientService addressClientService;

	@GetMapping("/member/address")
	public String registerForm() {
		return "member/address/address";
	}

	@PostMapping("/member/address")
	public ResponseEntity<Object> registerAddress(@ModelAttribute @Valid AddressRegisterRequest request) {
		ResponseEntity<Void> response = addressClientService.registerAddress(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/member/me/address")
	public String getAddressList(Model model) {
		ResponseEntity<List<AddressResponse>> responseEntity = addressClientService.getAddressList();
		List<AddressResponse> addressList = responseEntity.getBody();
		model.addAttribute("addressList", addressList);
		return "member/address/address";
	}

	@PutMapping("/member/address/{address-id}")
	public ResponseEntity<Object> updateAddress(@PathVariable(name = "address-id") Long addressId,
		@ModelAttribute @Valid AddressUpdateRequest request) {
		ResponseEntity<Void> response = addressClientService.updateAddress(addressId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/member/address/{address-id}")
	public ResponseEntity<Object> deleteAddress(@PathVariable(name = "address-id") Long addressId) {
		ResponseEntity<Void> response = addressClientService.deleteAddress(addressId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

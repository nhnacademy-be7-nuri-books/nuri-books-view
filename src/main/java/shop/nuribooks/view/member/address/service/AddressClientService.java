package shop.nuribooks.view.member.address.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.dto.request.AddressUpdateRequest;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;

public interface AddressClientService {

	ResponseEntity<Void> registerAddress(AddressRegisterRequest request);

	ResponseEntity<List<AddressResponse>> getAddressList();

	ResponseEntity<Void> deleteAddress(Long addressId);

	ResponseEntity<Void> updateAddress(Long addressId, AddressUpdateRequest request);
}

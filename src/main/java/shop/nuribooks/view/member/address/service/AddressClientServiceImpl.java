package shop.nuribooks.view.member.address.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.dto.request.AddressUpdateRequest;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;
import shop.nuribooks.view.member.address.feign.AddressClient;

@RequiredArgsConstructor
@Service
public class AddressClientServiceImpl implements AddressClientService {

	private final AddressClient addressClient;

	@Override
	public ResponseEntity<Void> registerAddress(AddressRegisterRequest request) {
		return addressClient.registerAddress(request);
	}

	@Override
	public ResponseEntity<List<AddressResponse>> getAddressList() {
		return addressClient.getAddressList();
	}

	@Override
	public ResponseEntity<Void> deleteAddress(Long addressId) {
		return addressClient.deleteAddress(addressId);
	}

	@Override
	public ResponseEntity<Void> updateAddress(Long addressId, AddressUpdateRequest request) {
		return addressClient.updateAddress(addressId, request);
	}
}

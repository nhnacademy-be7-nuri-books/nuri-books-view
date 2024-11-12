package shop.nuribooks.view.member.address.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;

public interface AddressClientService {

    void registerAddress(AddressRegisterRequest request);

    ResponseEntity<List<AddressResponse>> getAddressList();
}
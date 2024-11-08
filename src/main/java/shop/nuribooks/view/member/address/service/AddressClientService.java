package shop.nuribooks.view.member.address.service;

import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;

public interface AddressClientService {

    void registerAddress(AddressRegisterRequest request);
}

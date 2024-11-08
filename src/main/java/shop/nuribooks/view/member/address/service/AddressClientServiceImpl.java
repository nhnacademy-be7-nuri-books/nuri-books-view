package shop.nuribooks.view.member.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.feign.AddressClient;

@RequiredArgsConstructor
@Service
public class AddressClientServiceImpl implements AddressClientService{

    private final AddressClient addressClient;

    @Override
    public void registerAddress(AddressRegisterRequest request) {
        addressClient.registerAddress(request);
    }
}

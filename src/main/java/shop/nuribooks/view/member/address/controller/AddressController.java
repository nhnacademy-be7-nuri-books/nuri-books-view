package shop.nuribooks.view.member.address.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;
import shop.nuribooks.view.member.address.service.AddressClientService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AddressController {

    private final AddressClientService addressClientService;

    @GetMapping("api/member/address")
    public String registerForm() {
        return "member/address/address-register";
    }

    @PostMapping("api/member/address")
    public String registerAddress(@ModelAttribute @Valid AddressRegisterRequest request, HttpServletRequest httpServletRequest) {
        log.info("{}, {}, {}", request.zipcode(), request.address(), request.detailAddress());
        log.info(httpServletRequest.toString());
        addressClientService.registerAddress(request);

        return "index";
    }
}

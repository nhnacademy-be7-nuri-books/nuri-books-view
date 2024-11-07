package shop.nuribooks.view.member.address.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import shop.nuribooks.view.member.dto.request.AddressRegisterRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddressController {


    @GetMapping("/address/register")
    public String registerForm() {
        return "member/address/address-register";
    }

    @PostMapping("/address")
    public String registerAddress(@ModelAttribute AddressRegisterRequest request) {
        log.info("{}, {}, {}", request.zipcode(), request.address(), request.addressDetail());

        return "index";
    }
}

package shop.nuribooks.view.member.address.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import shop.nuribooks.view.member.address.dto.request.AddressRegisterRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AddressController {


    @GetMapping("/address/register")
    public String registerForm() {
        return "member/address/address-register";
    }

    @PostMapping("/address/register")
    public String registerAddress(@ModelAttribute @Valid AddressRegisterRequest request) {
        log.info("{}, {}, {}", request.zipcode(), request.address(), request.detailAddress());

        return "index";
    }
}

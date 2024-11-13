package shop.nuribooks.view.cart.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.request.CartAddRequestToServer;
import shop.nuribooks.view.cart.dto.response.CartResponse;
import shop.nuribooks.view.cart.service.CartClientService;
import shop.nuribooks.view.common.decoder.JwtDecoder;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartClientService cartClientService;

    @PostMapping("/api/cart")
    public String addCart(@ModelAttribute CartAddRequest cartAddRequest, HttpServletRequest request, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 회원인 경우
                if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                    String userId = JwtDecoder.getUserId(cookie.getValue());
                    CartAddRequestToServer cartAddRequestToServer = new CartAddRequestToServer(userId,
                        cartAddRequest.bookId(), cartAddRequest.quantity());
                    cartClientService.addCart(cartAddRequestToServer);
                    // 상세정보로 리다이렉트
                    return "index";
                }
                // 비회원인데 카트가 이미 있는 경우
                if (cookie.getName().equals("cart-id")) {
                    String cartId = cookie.getValue();
                    CartAddRequestToServer cartAddRequestToServer = new CartAddRequestToServer(cartId,
                        cartAddRequest.bookId(), cartAddRequest.quantity());
                    cartClientService.addCart(cartAddRequestToServer);
                    return "index";
                }
            }
        }
        // 비회원인데 처음 요청인 경우
        CartAddRequestToServer cartAddRequestToServer = new CartAddRequestToServer(uuid,
            cartAddRequest.bookId(), cartAddRequest.quantity());
        cartClientService.addCart(cartAddRequestToServer);
        Cookie cookie = new Cookie("cart-id", uuid);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return "index";
    }

    @GetMapping("/api/cart")
    public String getCart(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 회원인 경우
                if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                    String userId = JwtDecoder.getUserId(cookie.getValue());
                    List<CartResponse> cart = cartClientService.getCart(userId);
                    model.addAttribute("cartResponseList", cart);
                    return "cart";
                }
                // 비회원인데 카트가 이미 있는 경우
                if (cookie.getName().equals("cart-id")) {
                    String cartId = cookie.getValue();
                    List<CartResponse> cart = cartClientService.getCart(cartId);
                    model.addAttribute("cartResponseList", cart);
                    return "cart";
                }
            }
        }
        return "cart";
    }


}

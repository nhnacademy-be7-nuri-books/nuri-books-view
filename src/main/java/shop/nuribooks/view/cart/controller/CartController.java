package shop.nuribooks.view.cart.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.request.CartAddRequestToServer;
import shop.nuribooks.view.cart.dto.response.CartResponse;
import shop.nuribooks.view.cart.service.CartClientService;
import shop.nuribooks.view.common.decoder.JwtDecoder;

@RequiredArgsConstructor
@Controller
public class CartController {

	private final CartClientService cartClientService;

	@Value("${error.message-key}")
	private String errorMessageKey;
	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;
	@Value("${success.message-key}")
	private String successMessageKey;

	@PostMapping("/api/cart")
	public String addCart(@ModelAttribute CartAddRequest cartAddRequest, HttpServletRequest request,
		HttpServletResponse response, RedirectAttributes redirectAttributes) {


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
					redirectAttributes.addFlashAttribute(successMessageKey, "상품이 장바구니에 담겼습니다.");
					// 상세정보로 리다이렉트
					return "redirect:/view/book/details/" + cartAddRequest.bookId();
				}
				// 비회원인데 카트가 이미 있는 경우
				if (cookie.getName().equals("cart-id")) {
					String cartId = cookie.getValue();
					CartAddRequestToServer cartAddRequestToServer = new CartAddRequestToServer(cartId,
						cartAddRequest.bookId(), cartAddRequest.quantity());
					cartClientService.addCart(cartAddRequestToServer);
					redirectAttributes.addFlashAttribute(successMessageKey, "상품이 장바구니에 담겼습니다.");
					return "redirect:/view/book/details/" + cartAddRequest.bookId();
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

		redirectAttributes.addFlashAttribute(successMessageKey, "상품이 장바구니에 담겼습니다.");
		return "redirect:/view/book/details/" + cartAddRequest.bookId();
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

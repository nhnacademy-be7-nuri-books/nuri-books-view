package shop.nuribooks.view.cart.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.request.CartUpdateRequest;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;
import shop.nuribooks.view.cart.service.CartClientService;
import shop.nuribooks.view.common.decoder.JwtDecoder;
import shop.nuribooks.view.common.util.CookieUtil;

@RequiredArgsConstructor
@Controller
public class CartController {

	public static final String CART_COOKIE_ID = "cart-id";
	private final CartClientService cartClientService;

	@Value("${error.message-key}")
	private String errorMessageKey;
	@Value("${header.refresh-key-name}")
	private String refreshHeaderName;
	@Value("${success.message-key}")
	private String successMessageKey;

	private static BigDecimal getTotalPrice(List<CartBookResponse> cart) {
		return cart.stream()
			.map(CartBookResponse::totalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@PostMapping("/api/cart/{book-id}")
	public String addCart(@PathVariable("book-id") Long bookId, @RequestParam Integer quantity,
		HttpServletRequest request,
		HttpServletResponse response, RedirectAttributes redirectAttributes) {

		String cartId = getCartId(request);
		if (Objects.isNull(cartId)) {
			cartId = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(CART_COOKIE_ID, cartId);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
		}
		CartRequestToServer cartRequestToServer = new CartRequestToServer(cartId, bookId, quantity);
		cartClientService.addCart(cartRequestToServer);
		redirectAttributes.addFlashAttribute(successMessageKey, "상품이 장바구니에 담겼습니다.");
		return "redirect:/view/book/details/" + bookId;
	}

	@GetMapping("/api/cart")
	public String getCart(HttpServletRequest request, Model model) {
		String cartId = getCartId(request);
		if (Objects.isNull(cartId)) {
			return "cart";
		}
		List<CartBookResponse> cart = cartClientService.getCart(cartId);
		BigDecimal totalPrice = getTotalPrice(cart);
		model.addAttribute("cartBookResponseList", cart);
		model.addAttribute("totalPrice", totalPrice);
		return "cart";
	}

	@PostMapping("/api/cart/updateQuantity")
	public String updateCart(@ModelAttribute CartUpdateRequest cartUpdateRequest, HttpServletRequest request,
		RedirectAttributes redirectAttributes) {
		String cartId = getCartId(request);
		if (Objects.isNull(cartId)) {
			return "cart";
		}
		CartRequestToServer cartRequestToServer = new CartRequestToServer(cartId,
			cartUpdateRequest.bookId(), cartUpdateRequest.quantity());
		cartClientService.addCart(cartRequestToServer);
		redirectAttributes.addFlashAttribute(successMessageKey, "수량이 변경되었습니다");
		return "redirect:/api/cart";
	}

	@DeleteMapping("/api/cart/book/{book-id}")
	@RequestBody
	public ResponseEntity<Void> removeCartItem(@PathVariable("book-id") Long bookId, HttpServletRequest request) {

		String cartId = getCartId(request);
		if (Objects.isNull(cartId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return cartClientService.removeCartItem(cartId, bookId);
	}

	private String getCartId(HttpServletRequest request) {
		String accessToken = CookieUtil.findByCookieKey(request, HttpHeaders.AUTHORIZATION);
		if (Objects.nonNull(accessToken)) {
			return JwtDecoder.getUserId(accessToken);
		}
		String customerId = CookieUtil.findByCookieKey(request, CART_COOKIE_ID);
		if (Objects.nonNull(customerId)) {
			return customerId;
		}
		return null;
	}

}

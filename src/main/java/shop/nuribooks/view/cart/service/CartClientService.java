package shop.nuribooks.view.cart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;

public interface CartClientService {

    ResponseEntity<Void> addCart(CartRequestToServer request);

    List<CartBookResponse> getCart(String cartId);

    void loadCartToRedis(String userId);

    ResponseEntity<Void> removeCartItem(String cartId, Long bookId);
}

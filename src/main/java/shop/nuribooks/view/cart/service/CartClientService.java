package shop.nuribooks.view.cart.service;

import java.util.List;

import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;

public interface CartClientService {

    void addCart(CartRequestToServer request);

    List<CartBookResponse> getCart(String cartId);

    void loadCartToRedis(String userId);

    void removeCartItem(String cartId, Long bookId);
}

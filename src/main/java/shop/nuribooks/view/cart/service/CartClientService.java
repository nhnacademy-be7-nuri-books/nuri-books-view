package shop.nuribooks.view.cart.service;

import java.util.List;
import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.response.CartResponse;

public interface CartClientService {

    void addCart(CartAddRequest request);

    List<CartResponse> getCart(String cartId);
}

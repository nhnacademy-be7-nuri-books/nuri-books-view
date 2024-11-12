package shop.nuribooks.view.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.response.CartResponse;
import shop.nuribooks.view.cart.fegin.CartClient;

@RequiredArgsConstructor
@Service
public class CartClientServiceImpl implements CartClientService{

    private final CartClient cartClient;

    @Override
    public void addCart(CartAddRequest request) {
        cartClient.addCart(request);
    }

    @Override
    public List<CartResponse> getCart(String cartId) {
        ResponseEntity<List<CartResponse>> cart = cartClient.getCart(cartId);
        return cart.getBody();
    }
}

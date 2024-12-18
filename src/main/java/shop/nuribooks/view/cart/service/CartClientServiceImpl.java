package shop.nuribooks.view.cart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.request.CartLoadRequest;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;
import shop.nuribooks.view.cart.fegin.CartClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartClientServiceImpl implements CartClientService{

    private final CartClient cartClient;

    @Override
    public ResponseEntity<Void> addCart(CartRequestToServer request) {

        return cartClient.addCart(request);
    }

    @Override
    public List<CartBookResponse> getCart(String cartId) {
        ResponseEntity<List<CartBookResponse>> cart = cartClient.getCart(cartId);
        return cart.getBody();
    }

    @Override
    public void loadCartToRedis(String userId) {
        try {
            Long parsedUserId = Long.parseLong(userId);
            CartLoadRequest cartLoadRequest = new CartLoadRequest(parsedUserId);
            cartClient.loadCartToRedis(cartLoadRequest);
        } catch (Exception e) {
            log.error("예외를 무시합니다.");
		}
    }

    @Override
    public ResponseEntity<Void> removeCartItem(String cartId, Long bookId) {
        return cartClient.removeCartItem(cartId, bookId);
    }
}

package shop.nuribooks.view.cart.fegin;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.cart.dto.request.CartRequestToServer;
import shop.nuribooks.view.cart.dto.request.CartLoadRequest;
import shop.nuribooks.view.cart.dto.response.CartBookResponse;

@FeignClient(name = "cart", url = "http://localhost:8080")
public interface CartClient {

    @PostMapping("/api/cart")
    ResponseEntity<Void> addCart(@RequestBody CartRequestToServer request);

    @GetMapping("/api/cart/{cart-id}")
    ResponseEntity<List<CartBookResponse>> getCart(@PathVariable("cart-id") String cartId);

    @PostMapping("/api/cart/load-to-redis")
    ResponseEntity<Void> loadCartToRedis(@RequestBody CartLoadRequest request);

    @DeleteMapping("/api/cart/{cart-id}/book/{book-id}")
    ResponseEntity<Void> removeCartItem(@PathVariable("cart-id") String cartId, @PathVariable("book-id") Long bookId);
}

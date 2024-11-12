package shop.nuribooks.view.cart.fegin;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.nuribooks.view.cart.dto.request.CartAddRequest;
import shop.nuribooks.view.cart.dto.response.CartResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "cart", url = "http://localhost:8080")
public interface CartClient {

    @PostMapping("/api/cart")
    ResponseEntity<ResponseMessage> addCart(@RequestBody CartAddRequest request);

    @GetMapping("/api/cart/{cart-id}")
    ResponseEntity<List<CartResponse>> getCart(@PathVariable("cart-id") String cartId);
}

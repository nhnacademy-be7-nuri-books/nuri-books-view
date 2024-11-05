package shop.nuribooks.view.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "book", url = "http://localhost:8083")
public interface BookServiceClient {
}

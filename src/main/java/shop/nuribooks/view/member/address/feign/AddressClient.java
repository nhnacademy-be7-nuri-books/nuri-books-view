package shop.nuribooks.view.member.address.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "address", url = "http://localhost:8080")
public interface AddressClient {

}

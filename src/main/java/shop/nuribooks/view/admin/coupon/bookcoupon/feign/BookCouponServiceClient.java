package shop.nuribooks.view.admin.coupon.bookcoupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "bookCoupon", url = "http://localhost:8080")

public interface BookCouponServiceClient {

	@GetMapping("/api/coupons/book-coupons/{book-id}")
	ResponseEntity<BookCouponResponse> getBookCoupon(@PathVariable(name = "book-id") Long bookId);

	@PostMapping("/api/coupons/book-coupons")
	ResponseEntity<ResponseMessage> registerBookCoupon(@Valid @RequestBody BookCouponRequest bookCouponRequest);

}

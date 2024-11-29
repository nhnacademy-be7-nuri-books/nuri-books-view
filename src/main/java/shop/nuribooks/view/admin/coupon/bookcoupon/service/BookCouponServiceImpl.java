package shop.nuribooks.view.admin.coupon.bookcoupon.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponResponse;
import shop.nuribooks.view.admin.coupon.bookcoupon.feign.BookCouponServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class BookCouponServiceImpl implements BookCouponService {
	private final BookCouponServiceClient bookCouponServiceClient;

	@Override
	public BookCouponResponse getBookCoupon(Long id) {
		return this.bookCouponServiceClient.getBookCoupon(id).getBody();
	}

	@Override
	public ResponseMessage registerBookCoupon(BookCouponRequest bookCouponRequest) {
		return bookCouponServiceClient.registerBookCoupon(bookCouponRequest).getBody();
	}
}

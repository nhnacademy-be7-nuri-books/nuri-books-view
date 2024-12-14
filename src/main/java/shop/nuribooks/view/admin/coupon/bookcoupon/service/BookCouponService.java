package shop.nuribooks.view.admin.coupon.bookcoupon.service;

import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface BookCouponService {

	ResponseMessage registerBookCoupon(BookCouponRequest bookCouponRequest);

	BookCouponResponse getBookCoupon(Long bookId);

}

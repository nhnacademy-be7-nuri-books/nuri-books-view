package shop.nuribooks.view.admin.coupon.enums;

public enum CouponType {
	ALL("전체"), BOOK("도서"), CATEGORY("카테고리");

	String kor;

	CouponType(String kor) {
		this.kor = kor;
	}

	public String toKorName() {
		return this.kor;
	}
}

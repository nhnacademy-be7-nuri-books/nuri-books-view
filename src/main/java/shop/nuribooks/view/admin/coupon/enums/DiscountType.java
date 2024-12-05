package shop.nuribooks.view.admin.coupon.enums;

public enum DiscountType {
	RATED("비율(%)"), FIXED("정량(+)");
	String kor;

	DiscountType(String kor) {
		this.kor = kor;
	}

	public String toKorName() {
		return this.kor;
	}
}

package shop.nuribooks.view.admin.coupon.enums;

public enum IssuanceType {
	LIMITED("한정"), UNLIMITED("무한");

	String kor;

	IssuanceType(String kor) {
		this.kor = kor;
	}

	public String toKorName() {
		return this.kor;
	}
}

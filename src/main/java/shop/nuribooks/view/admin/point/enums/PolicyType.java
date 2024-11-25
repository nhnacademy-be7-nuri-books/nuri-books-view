package shop.nuribooks.view.admin.point.enums;

public enum PolicyType {
	FIXED("정량(+)"), RATED("비율(%)");

	String kor;

	PolicyType(String kor) {
		this.kor = kor;
	}

	public String toKorName() {
		return this.kor;
	}

}

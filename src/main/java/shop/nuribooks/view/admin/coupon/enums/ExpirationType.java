package shop.nuribooks.view.admin.coupon.enums;

public enum ExpirationType {
	DATE("만료일 지정"), DAYS("기간 지정");

	String kor;

	ExpirationType(String kor){
		this.kor = kor;
	}

	public String toKorName(){
		return this.kor;
	}
}

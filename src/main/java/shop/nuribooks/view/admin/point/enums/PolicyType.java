package shop.nuribooks.view.admin.point.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PolicyType {
	RATED("비율(%)"), FIXED("정량(+)");

	String kor;

	PolicyType(String kor){
		this.kor = kor;
	}

	public String toKorName(){
		return this.kor;
	}
}

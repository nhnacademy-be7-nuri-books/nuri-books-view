package shop.nuribooks.view.member.entity;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderType {

	MALE, FEMALE;

	@JsonValue
	public String getValue() {
		return name();
	}

	@JsonCreator
	public static GenderType fromValue(String value) {
		return Stream.of(GenderType.values())
			.filter(genderType -> genderType.getValue().equals(value.toUpperCase()))
			.findFirst()
			.orElse(null);
	}
}

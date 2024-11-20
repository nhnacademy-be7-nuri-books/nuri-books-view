package shop.nuribooks.view.booksearch.enums;

public enum SortType {
	ACCURACY("정확도순"),
	// 구현 예정
	POPULAR("인기도순"),

	NEW("발매일순"),
	CHEAP("낮은가격순"),
	EXPENSIVE("높은가격순"),
	STAR("평점순"),
	REVIEW_COUNT("리뷰순");

	private String korName;

	SortType(String korName) {
		this.korName = korName;
	}

	public static SortType convert(String str) {
		SortType type;
		try {
			type = SortType.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			type = SortType.ACCURACY;
		}
		return type;
	}

	public String getKorName() {
		return this.korName;
	}
}

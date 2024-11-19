package shop.nuribooks.view.booksearch.enums;

public enum SearchType {
	ALL("통합 검색"),
	TITLE("제목 검색"),
	DESCRIPTION("설명 검색"),
	CONTRIBUTOR("작가 검색");

	private String korName;

	SearchType(String korName) {
		this.korName = korName;
	}

	public static SearchType convert(String str) {
		SearchType type;
		try {
			type = SearchType.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			type = SearchType.ALL;
		}
		return type;
	}

	public String toKorName(){
		return this.korName;
	}
}

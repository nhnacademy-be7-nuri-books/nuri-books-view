package shop.nuribooks.view.book.enums;

public enum SortType {
	TITLE_ASC("제목순 (오름차순)"),
	TITLE_DESC("제목순 (내림차순)"),
	NEW("발매일순"),
	CHEAP("낮은가격순"),
	EXPENSIVE("높은가격순"),
	STAR_DESC("평점높은순"),
	STAR_ASC("평점낮은순"),
	REVIEW_COUNT_DESC("리뷰많은순"),
	REVIEW_COUNT_ASC("리뷰적은순");

	private String korName;

	SortType(String korName) {
		this.korName = korName;
	}

	public String getKorName() {
		return korName;
	}
}

package shop.nuribooks.view.book.dto;

public record TopBookLikeResponse(
	Long bookId,
	String thumbnailUrl,
	String title
) {
}

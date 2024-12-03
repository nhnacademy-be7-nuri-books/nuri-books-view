package shop.nuribooks.view.book.dto;

public record TopBookResponse(
	Long bookId,
	String thumbnailUrl,
	String title
) {
}

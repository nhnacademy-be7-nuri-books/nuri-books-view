package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BookResponse(
	Long id,
	String publisherName,
	String state,
	String title,
	String thumbnailImageUrl,
	String detailImageUrl,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDate publicationDate,
	BigDecimal price,
	int discountRate,
	String description,
	String contents,
	String isbn,
	boolean isPackageable,
	int likeCount,
	int stock,
	Long viewCount
) {
}

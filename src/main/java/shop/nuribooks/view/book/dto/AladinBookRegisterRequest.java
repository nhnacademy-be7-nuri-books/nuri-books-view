package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AladinBookRegisterRequest extends BaseBookRegisterRequest{
	@NotBlank(message = "카테고리명은 필수입니다.")
	private final String categoryName;

	public AladinBookRegisterRequest(
		String title,
		String author,
		String publisherName,
		LocalDate publicationDate,
		BigDecimal price,
		int discountRate,
		int stock,
		String state,
		String thumbnailImageUrl,
		String detailImageUrl,
		String description,
		String contents,
		String isbn,
		boolean isPackageable,
		List<Long> tagIds,
		String categoryName
	) {
		super(title, author, publisherName, publicationDate, price, discountRate, stock, state, thumbnailImageUrl,
			detailImageUrl, description, contents, isbn, isPackageable, tagIds);
		this.categoryName = categoryName;
	}
}

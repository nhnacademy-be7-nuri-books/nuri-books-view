package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PersonallyBookRegisterRequest extends BaseBookRegisterRequest{
	@Size(min = 1, max = 10, message = "카테고리는 최대 10개까지 선택 가능합니다.")
	private final List<Long> categoryIds;

	public PersonallyBookRegisterRequest(
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
		List<Long> categoryIds
	) {
		super(title, author, publisherName, publicationDate, price, discountRate, stock, state, thumbnailImageUrl,
			detailImageUrl, description, contents, isbn, isPackageable, tagIds);
		this.categoryIds = categoryIds;
	}
}

package shop.nuribooks.view.booksearch.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BookSearchResponse(
	Long id,
	String publisher_name,
	String state,
	String title,
	String description,
	BigDecimal price,
	BigDecimal sale_price,
	int discount_rate,
	String thumbnail_image_url,
	long view_count,
	int review_count,
	BigDecimal total_score,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	LocalDate publication_date,
	List<String>tags,
	List<ContributorResponse> contributors) {
}

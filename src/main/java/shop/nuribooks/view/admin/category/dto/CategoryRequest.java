package shop.nuribooks.view.admin.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 카테고리 등록/업데이트 요청 DTO.
 *
 * @param name 카테고리 이름
 */
public record CategoryRequest(
	@NotBlank(message = "카테고리 이름은 필수입니다.")
	@Size(max = 30, message = "카테고리 이름은 최대 30자까지 가능합니다.")
	String name
) {
}
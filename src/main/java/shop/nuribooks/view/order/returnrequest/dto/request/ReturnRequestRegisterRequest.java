package shop.nuribooks.view.order.returnrequest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReturnRequestRegisterRequest(@NotBlank(message = "반품 요청 내용은 필수입니다.")
										   String contents,
										   @NotBlank(message = "반품 요청 이미지는 필수입니다.")
										   String imageUrl) {
}

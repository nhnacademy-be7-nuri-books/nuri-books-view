package shop.nuribooks.view.member.address.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressUpdateRequest(@NotBlank String zipcode,
								   @NotBlank String address,
								   @NotBlank String detailAddress,
								   @NotBlank String name) {
}

package shop.nuribooks.view.member.address.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AddressResponse(
        @NotBlank Long id,
        @NotBlank String name,
        @NotBlank String zipcode,
        @NotBlank String address,
        @NotBlank String detailAddress,
        boolean isDefault) {
}
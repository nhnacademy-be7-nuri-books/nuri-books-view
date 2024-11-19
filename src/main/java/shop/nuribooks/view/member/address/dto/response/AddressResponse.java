package shop.nuribooks.view.member.address.dto.response;


public record AddressResponse(
        Long id,
        String name,
        String zipcode,
        String address,
        String detailAddress,
        boolean isDefault) {
}

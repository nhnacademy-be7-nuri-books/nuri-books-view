package shop.nuribooks.view.member.customer;

import java.math.BigDecimal;
import java.util.List;

import shop.nuribooks.view.member.address.dto.response.AddressResponse;

public record CustomerDto(
	Long userId,
	String name,
	String phoneNumber,
	String email,
	BigDecimal point,
	List<AddressResponse> addressList
) {
}

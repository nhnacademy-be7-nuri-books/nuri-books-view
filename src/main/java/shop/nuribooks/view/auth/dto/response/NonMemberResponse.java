package shop.nuribooks.view.auth.dto.response;

public record NonMemberResponse(
	Long customerId,
	String email
) {
}

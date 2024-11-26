package shop.nuribooks.view.auth.dto.request;

public record NonMemberRequest(
	String email,
	String password
) {
}

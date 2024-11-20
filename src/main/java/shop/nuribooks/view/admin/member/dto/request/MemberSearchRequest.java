package shop.nuribooks.view.admin.member.dto.request;

import lombok.Builder;

@Builder
public record MemberSearchRequest(

	String name,
	String username,
	String email,
	String phoneNumber,
	String gender,
	String gradeName,
	String status,
	String authority,
	Integer page,
	Integer size

) {
	public MemberSearchRequest {
		if (page == null || page <= 0) {
			page = 0;
		}
		if (size == null || size <= 0) {
			size = 20;
		}
	}
}

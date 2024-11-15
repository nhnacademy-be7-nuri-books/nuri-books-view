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
	int page,
	int size

) { public MemberSearchRequest {
	if (page <= 0) {
		page = 0;
	}
	if (size <= 0) {
		size = 30;
	}
}}

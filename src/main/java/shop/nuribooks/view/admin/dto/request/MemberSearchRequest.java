package shop.nuribooks.view.admin.dto.request;

import lombok.Builder;

@Builder
public record MemberSearchRequest(

    String name,
    String email,
	String phoneNumber,
	String gender,
    String status,
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

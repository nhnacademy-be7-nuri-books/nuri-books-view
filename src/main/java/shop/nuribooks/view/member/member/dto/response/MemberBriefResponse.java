package shop.nuribooks.view.member.member.dto.response;

import lombok.Builder;

/**
 * member의 간단한 정보를 담은 dto
 * @param id
 * @param username
 */
@Builder
public record MemberBriefResponse(
	long id,
	String username
) {
}

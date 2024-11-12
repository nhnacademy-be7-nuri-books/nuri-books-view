package shop.nuribooks.view.review.dto.response;

import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import shop.nuribooks.view.member.dto.response.MemberBriefResponse;

/**
 * 작성자 정보를 함께 담은 review dto
 * @param id
 * @param title
 * @param content
 * @param score
 * @param member
 */
@Builder
public record ReviewMemberResponse(
	long id,
	String title,
	String content,
	int score,
	MemberBriefResponse member,
	List<ReviewImageResponse> reviewImages
) {
}

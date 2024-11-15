package shop.nuribooks.view.admin.member.service;

import org.springframework.data.domain.Page;

import shop.nuribooks.view.admin.member.dto.request.MemberSearchRequest;
import shop.nuribooks.view.admin.member.dto.response.MemberSearchResponse;

public interface AdminMemberService {

	Page<MemberSearchResponse> memberSearchWithPaging(MemberSearchRequest request);
}

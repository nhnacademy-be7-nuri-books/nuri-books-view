package shop.nuribooks.view.admin.member.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.member.dto.request.MemberSearchRequest;
import shop.nuribooks.view.admin.member.dto.response.MemberSearchResponse;
import shop.nuribooks.view.admin.member.feign.AdminMemberServiceClient;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

	private final AdminMemberServiceClient adminMemberServiceClient;

	@Override
	public Page<MemberSearchResponse> memberSearchWithPaging(MemberSearchRequest request) {

		return adminMemberServiceClient.memberSearchWithPaging(
			request.name(),
			request.username(),
			request.email(),
			request.phoneNumber(),
			request.gender(),
			request.gradeName(),
			request.status(),
			request.authority(),
			request.page(),
			request.size()).getBody();
	}
}

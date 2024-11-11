package shop.nuribooks.view.member.dto.request;

import lombok.Builder;

@Builder
public record MemberUpdateRequest (

    String name,
    String password,
    String phoneNumber
) {}


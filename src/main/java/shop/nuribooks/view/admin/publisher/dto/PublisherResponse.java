package shop.nuribooks.view.admin.publisher.dto;

import lombok.Builder;

@Builder
public record PublisherResponse(Long id, String name) {
}

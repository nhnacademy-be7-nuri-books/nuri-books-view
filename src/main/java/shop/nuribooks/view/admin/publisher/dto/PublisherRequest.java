package shop.nuribooks.view.admin.publisher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PublisherRequest(@NotBlank String name) {
}

package shop.nuribooks.view.admin.contributor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record ContributorRequest(@NotBlank @Length(min = 1, max = 50) String name) {}

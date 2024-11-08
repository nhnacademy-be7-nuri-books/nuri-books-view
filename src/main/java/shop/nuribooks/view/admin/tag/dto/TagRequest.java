package shop.nuribooks.view.admin.tag.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TagRequest (@NotBlank String name){
}

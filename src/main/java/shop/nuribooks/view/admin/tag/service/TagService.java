package shop.nuribooks.view.admin.tag.service;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    void registerTag(@Valid TagRequest tagRequest);
    List<TagResponse> getAllTags();

    Page<TagResponse> getAllTags(Pageable pageable);

    TagResponse getTag(Long id);

    void updateTag(@Valid Long id, TagRequest request);

    void deleteTag(@Valid Long id);
}

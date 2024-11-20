package shop.nuribooks.view.admin.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface TagService {
	ResponseMessage registerTag(@Valid TagRequest tagRequest);

	List<TagResponse> getAllTags();

	Page<TagResponse> getAllTags(Pageable pageable);

	TagResponse getTag(Long id);

	ResponseMessage updateTag(@Valid Long id, TagRequest request);

	ResponseMessage deleteTag(@Valid Long id);
}

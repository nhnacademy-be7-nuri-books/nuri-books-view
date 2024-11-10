package shop.nuribooks.view.admin.tag.service;

import java.util.List;

import shop.nuribooks.view.admin.tag.dto.TagResponse;

public interface TagService {
	List<TagResponse> getAllTags();
}

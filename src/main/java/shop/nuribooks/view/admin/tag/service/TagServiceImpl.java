package shop.nuribooks.view.admin.tag.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.feign.TagServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagServiceImpl implements TagService {

	private final TagServiceClient tagServiceClient;

	@Override
	public ResponseMessage registerTag(TagRequest tagRequest) {
		return tagServiceClient.registerTag(tagRequest).getBody();

	}

	@Override
	public List<TagResponse> getAllTags() {
		try {
			return tagServiceClient.getAllTags().getBody();
		} catch (FeignException ex) {
			return Collections.emptyList();
		}
	}

	@Override
	public Page<TagResponse> getAllTags(Pageable pageable) {
		try {
			return tagServiceClient.getAllTags(pageable.getPageNumber(), pageable.getPageSize()).getBody();
		} catch (FeignException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	@Override
	public TagResponse getTag(Long contributorId) {
		return tagServiceClient.getTag(contributorId).getBody();

	}

	@Override
	public ResponseMessage updateTag(Long id, TagRequest request) {
		return tagServiceClient.updateTag(id, request).getBody();

	}

	@Override
	public ResponseMessage deleteTag(Long id) {
		return tagServiceClient.deleteTag(id).getBody();
	}
}

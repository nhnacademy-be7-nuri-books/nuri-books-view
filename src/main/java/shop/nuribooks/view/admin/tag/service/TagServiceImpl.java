package shop.nuribooks.view.admin.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.feign.TagServiceClient;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
	private final TagServiceClient tagServiceClient;

	@Override
	public List<TagResponse> getAllTags() {
		return tagServiceClient.getAllTags().getBody();
	}
}

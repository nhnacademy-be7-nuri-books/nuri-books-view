package shop.nuribooks.view.admin.tag.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.admin.tag.TagServiceClient;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagServiceClient tagService;

    @Override
    public void registerTag(TagRequest tagRequest) {
        try {
            TagResponse response = tagService.registerTag(tagRequest).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    @Override
    public List<TagResponse> getAllTags() {
        try {
            return tagService.getAllTags().getBody();
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }
}

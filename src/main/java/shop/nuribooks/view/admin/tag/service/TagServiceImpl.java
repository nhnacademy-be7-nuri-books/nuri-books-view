package shop.nuribooks.view.admin.tag.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.admin.tag.dto.TagRequest;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.feign.TagServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagServiceClient tagServiceClient;

    @Override
    public void registerTag(TagRequest tagRequest) {
        try {
            TagResponse response = tagServiceClient.registerTag(tagRequest).getBody();
            log.info("response :{}", response);
            log.info("request :{}", tagRequest.name());
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
            return tagServiceClient.getAllTags().getBody();
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateTag(Long id, TagRequest request) {
        try {
            TagResponse response = tagServiceClient.updateTag(id, request).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    @Override
    public void deleteTag(Long id) {
        try {
            tagServiceClient.deleteTag(id);
        } catch (FeignException ex) {
            ExceptionUtil.handleFeignException(ex);
        }
    }
}

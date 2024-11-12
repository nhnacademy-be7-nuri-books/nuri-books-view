package shop.nuribooks.view.admin.publisher.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.publisher.PublisherServiceClient;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherServiceClient publisherServiceClient;
    @Value("${success.message-key}")
    private String successMessageKey;

    @Override
    public void registerPublisher(PublisherRequest publisherRequest) {
        try {
            PublisherResponse response = publisherServiceClient.registerPublisher(publisherRequest).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    @Override
    public List<PublisherResponse> getAllPublishers() {
        try {
            return publisherServiceClient.getAllPublishers().getBody();
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public PublisherResponse getPublisher(Long publisherId) {
        try {
            return publisherServiceClient.getPublisherById(publisherId).getBody();
        } catch (FeignException ex) {
            String errorMessage = ExceptionUtil.handleFeignException(ex);
            return PublisherResponse.error(errorMessage);
        }
    }

    public void updatePublisher(Long id, PublisherRequest publisherRequest) {
        try {
            PublisherResponse response = publisherServiceClient.updatePublisher(id, publisherRequest).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    @Override
    public void deletePublisher(Long id) {
        try {
            publisherServiceClient.deletePublisher(id);
        } catch (FeignException ex) {
            ExceptionUtil.handleFeignException(ex);
        }
    }
}

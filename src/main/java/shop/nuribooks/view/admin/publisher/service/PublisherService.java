package shop.nuribooks.view.admin.publisher.service;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {
    void registerPublisher(@Valid PublisherRequest publisherRequest);

    Page<PublisherResponse> getAllPublishers(Pageable pageable);

    PublisherResponse getPublisher(Long id);

    void updatePublisher(@Valid Long id, PublisherRequest request);

    void deletePublisher(@Valid Long id);
}

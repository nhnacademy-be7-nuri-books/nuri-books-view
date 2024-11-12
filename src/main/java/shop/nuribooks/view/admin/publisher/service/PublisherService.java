package shop.nuribooks.view.admin.publisher.service;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;

import java.util.List;

public interface PublisherService {
    void registerPublisher(@Valid PublisherRequest publisherRequest);

    List<PublisherResponse> getAllPublishers();

    PublisherResponse getPublisher(Long id);

    void updatePublisher(@Valid Long id, PublisherRequest request);

    void deletePublisher(@Valid Long id);
}

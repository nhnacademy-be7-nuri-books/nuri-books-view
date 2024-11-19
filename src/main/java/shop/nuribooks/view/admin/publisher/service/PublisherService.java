package shop.nuribooks.view.admin.publisher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface PublisherService {
	ResponseMessage registerPublisher(@Valid PublisherRequest publisherRequest);

	Page<PublisherResponse> getAllPublishers(Pageable pageable);

	PublisherResponse getPublisher(Long id);

	ResponseMessage updatePublisher(@Valid Long id, PublisherRequest request);

	ResponseMessage deletePublisher(@Valid Long id);
}

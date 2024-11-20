package shop.nuribooks.view.admin.publisher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.publisher.PublisherServiceClient;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

	private final PublisherServiceClient publisherServiceClient;

	@Override
	public ResponseMessage registerPublisher(PublisherRequest publisherRequest) {
		return publisherServiceClient.registerPublisher(publisherRequest).getBody();

	}

	@Override
	public Page<PublisherResponse> getAllPublishers(Pageable pageable) {
		return publisherServiceClient.getAllPublishers(pageable.getPageNumber(), pageable.getPageSize()).getBody();

	}

	@Override
	public PublisherResponse getPublisher(Long publisherId) {
		return publisherServiceClient.getPublisherById(publisherId).getBody();
	}

	public ResponseMessage updatePublisher(Long id, PublisherRequest publisherRequest) {
		return publisherServiceClient.updatePublisher(id, publisherRequest).getBody();
	}

	@Override
	public ResponseMessage deletePublisher(Long id) {
		return publisherServiceClient.deletePublisher(id).getBody();

	}
}

package shop.nuribooks.view.admin.contributor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.feign.ContributorServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@Service
public class ContributorServiceImpl implements ContributorService {
	private final ContributorServiceClient contributorServiceClient;

	@Override
	public ResponseMessage registerContributor(ContributorRequest contributorRequest) {
		return contributorServiceClient.registerContributor(contributorRequest).getBody();

	}

	@Override
	public Page<ContributorResponse> getAllContributors(Pageable pageable) {
		return contributorServiceClient.getAllContributors(pageable.getPageNumber(), pageable.getPageSize())
			.getBody();

	}

	@Override
	public ContributorResponse getContributor(Long contributorId) {
		return contributorServiceClient.getContributor(contributorId).getBody();

	}

	public ResponseMessage updateContributor(Long id, ContributorRequest contributorRequest) {
		return contributorServiceClient.updateContributor(id, contributorRequest).getBody();

	}

	@Override
	public ResponseMessage deleteContributor(Long id) {
		return contributorServiceClient.deleteContributor(id).getBody();

	}
}

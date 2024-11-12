package shop.nuribooks.view.admin.contributor.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.feign.ContributorServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

@RequiredArgsConstructor
@Service
public class ContributorServiceImpl implements ContributorService {
	private final ContributorServiceClient contributorServiceClient;
	@Value("${success.message-key}")
	private String successMessageKey;

	@Override
	public String registerContributor(ContributorRequest contributorRequest) {
		try {
			ContributorResponse response = contributorServiceClient.registerContributor(contributorRequest).getBody();
			return successMessageKey + Objects.requireNonNull(response).name();
		} catch (FeignException ex) {
			return ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public List<ContributorResponse> getAllContributors() {
		try {
			return contributorServiceClient.getAllContributors().getBody();
		} catch (FeignException ex) {
			return Collections.emptyList();
		}
	}

	@Override
	public ContributorResponse getContributor(Long contributorId) {
		try {
			return contributorServiceClient.getContributor(contributorId).getBody();
		} catch (FeignException ex) {
			String errorMessage = ExceptionUtil.handleFeignException(ex);
			return ContributorResponse.error(errorMessage);
		}
	}

	public String updateContributor(Long id, ContributorRequest contributorRequest) {
		try {
			ContributorResponse response = contributorServiceClient.updateContributor(id, contributorRequest).getBody();
			return successMessageKey + Objects.requireNonNull(response).name();
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.CONFLICT.value()) {
				throw new ResourceAlreadyExistsException(ex.getMessage());
			}
			return ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public String deleteContributor(Long id) {
		try {
			contributorServiceClient.deleteContributor(id);
			return successMessageKey;
		} catch (FeignException ex) {
			return ExceptionUtil.handleFeignException(ex);
		}
	}
}

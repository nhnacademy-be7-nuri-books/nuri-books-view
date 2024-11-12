package shop.nuribooks.view.admin.contributor.service;

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;

import java.util.List;

public interface ContributorService {
    List<ContributorResponse> getAllContributors();

    ContributorResponse getContributor(Long contributorId);
    String registerContributor(ContributorRequest contributorRequest);

    String updateContributor(Long id, ContributorRequest contributorRequest);

    String deleteContributor(Long contributorId);
}

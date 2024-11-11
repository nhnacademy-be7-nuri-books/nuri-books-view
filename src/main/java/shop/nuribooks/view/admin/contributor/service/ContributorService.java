package shop.nuribooks.view.admin.contributor.service;

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;

import java.util.List;

public interface ContributorService {
    List<ContributorResponse> getAllContributors();

    void registerContributor(ContributorRequest contributorRequest);

    void updateContributor(Long id, ContributorRequest contributorRequest);

    void deleteContributor(Long contributorId);
}

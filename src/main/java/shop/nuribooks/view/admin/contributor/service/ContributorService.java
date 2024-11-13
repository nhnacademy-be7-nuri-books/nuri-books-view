package shop.nuribooks.view.admin.contributor.service;

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContributorService {
    Page<ContributorResponse> getAllContributors(Pageable pageable);

    ContributorResponse getContributor(Long contributorId);
    String registerContributor(ContributorRequest contributorRequest);

    String updateContributor(Long id, ContributorRequest contributorRequest);

    String deleteContributor(Long contributorId);
}

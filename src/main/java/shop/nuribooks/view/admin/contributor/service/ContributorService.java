package shop.nuribooks.view.admin.contributor.service;

import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContributorService {
    Page<ContributorResponse> getAllContributors(Pageable pageable);

    ContributorResponse getContributor(Long contributorId);
    ResponseMessage registerContributor(ContributorRequest contributorRequest);

    ResponseMessage updateContributor(Long id, ContributorRequest contributorRequest);

    ResponseMessage deleteContributor(Long contributorId);
}

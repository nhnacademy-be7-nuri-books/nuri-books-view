package shop.nuribooks.view.admin.contributor.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import shop.nuribooks.view.admin.contributor.dto.ContributorRequest;
import shop.nuribooks.view.admin.contributor.dto.ContributorResponse;
import shop.nuribooks.view.admin.contributor.feign.ContributorServiceClient;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ContributorServiceImpl implements ContributorService {
    private final ContributorServiceClient contributorServiceClient;
    @Value("${success.message-key}")
    private String successMessageKey;

    //출판사 등록
    @Override
    public void registerContributor(ContributorRequest contributorRequest) {
        try {
            ContributorResponse response = contributorServiceClient.registerContributor(contributorRequest).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    //출판사 목록 조회
    @Override
    public List<ContributorResponse> getAllContributors() {
        try {
            return contributorServiceClient.getAllContributors().getBody();
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }

    // 기여자 수정
    public void updateContributor(Long id, ContributorRequest contributorRequest) {
        try {
            ContributorResponse response = contributorServiceClient.updateContributor(id, contributorRequest).getBody();
            Objects.requireNonNull(response);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.CONFLICT.value()) {
                throw new ResourceAlreadyExistsException(ex.getMessage());
            }
            ExceptionUtil.handleFeignException(ex);
        }
    }

    //기여자 삭제
    @Override
    public void deleteContributor(Long id) {
        try {
            contributorServiceClient.deleteContributor(id);
        } catch (FeignException ex) {
            ExceptionUtil.handleFeignException(ex);
        }
    }
}

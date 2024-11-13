package shop.nuribooks.view.admin.point.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.point.dto.PointPolicyRequest;
import shop.nuribooks.view.admin.point.dto.PointPolicyResponse;
import shop.nuribooks.view.admin.point.feign.PointPolicyServiceClient;
import shop.nuribooks.view.admin.point.service.PointPolicyService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {
	private final PointPolicyServiceClient pointPolicyServiceClient;

	@Override
	public Page<PointPolicyResponse> getPointPolicies(Pageable pageable) {
		return this.pointPolicyServiceClient.getPointPolicies(pageable).getBody();
	}

	@Override
	public ResponseMessage registerPointPolicy(PointPolicyRequest pointPolicyRequest) {
		return this.pointPolicyServiceClient.registerPointPolicy(pointPolicyRequest).getBody();
	}

	@Override
	public ResponseMessage updatePointPolicy(long id, PointPolicyRequest pointPolicyRequest) {
		return this.pointPolicyServiceClient.updatePointPolicy(id, pointPolicyRequest).getBody();
	}

	@Override
	public ResponseMessage deletePointPolicy(long id) {
		return this.pointPolicyServiceClient.deletePointPolicy(id).getBody();
	}
}

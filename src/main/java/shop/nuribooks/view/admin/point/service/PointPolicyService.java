package shop.nuribooks.view.admin.point.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.point.dto.PointPolicyRequest;
import shop.nuribooks.view.admin.point.dto.PointPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface PointPolicyService {
	Page<PointPolicyResponse> getPointPolicies(Pageable pageable);

	ResponseMessage registerPointPolicy(PointPolicyRequest pointPolicyRequest);

	ResponseMessage updatePointPolicy(long id, PointPolicyRequest pointPolicyRequest);

	ResponseMessage deletePointPolicy(long id);
}

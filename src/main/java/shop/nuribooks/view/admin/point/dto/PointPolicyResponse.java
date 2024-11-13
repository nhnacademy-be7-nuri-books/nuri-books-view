package shop.nuribooks.view.admin.point.dto;

import java.math.BigDecimal;

import shop.nuribooks.view.admin.point.enums.PolicyType;

public record PointPolicyResponse(long id, PolicyType policyType, String name, BigDecimal amount) {
}

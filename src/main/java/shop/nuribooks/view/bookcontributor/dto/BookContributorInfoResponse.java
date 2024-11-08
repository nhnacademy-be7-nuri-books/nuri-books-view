package shop.nuribooks.view.bookcontributor.dto;

public record BookContributorInfoResponse(
	Long contributorId,
	String contributorName,
	Long contributorRoleId,
	String contributorRoleName
) {
}

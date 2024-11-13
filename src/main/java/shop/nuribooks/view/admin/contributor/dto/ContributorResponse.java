package shop.nuribooks.view.admin.contributor.dto;

public record ContributorResponse(Long id, String name) {

	public static ContributorResponse error(String message) {
		return new ContributorResponse(null, message);
	}
}

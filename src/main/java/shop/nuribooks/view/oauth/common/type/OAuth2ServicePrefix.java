package shop.nuribooks.view.oauth.common.type;

public enum OAuth2ServicePrefix {
	NAVER("NAVER-"),
	PAYCO("PAYCO-");

	private final String servicePrefix;

	OAuth2ServicePrefix(String servicePrefix) {
		this.servicePrefix = servicePrefix;
	}

	@Override
	public String toString() {
		return servicePrefix;
	}
}

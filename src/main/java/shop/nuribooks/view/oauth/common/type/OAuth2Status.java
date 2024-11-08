package shop.nuribooks.view.oauth.common.type;

public enum OAuth2Status {
	NEED_REGISTER("NEED_REGISTER"),
	LOGIN_SUCCESS("LOGIN_SUCCESS"),
	ALREADY_EXISTS("ALREADY_EXISTS");

	private String message;

	OAuth2Status(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}

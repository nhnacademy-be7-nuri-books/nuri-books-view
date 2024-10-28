package shop.nuribooks.view.exception;

public class DefaultServerError extends RuntimeException {
	public DefaultServerError(int status, String message) {
		super(String.format("%d : %s", status, message));
	}
}

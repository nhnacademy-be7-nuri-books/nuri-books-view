package shop.nuribooks.view.exception;

public class ApiErrorException extends RuntimeException {
	private final int statusCode;
	private final String errorMessage;

	public ApiErrorException(int statusCode, String errorMessage) {
		super(errorMessage);
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}

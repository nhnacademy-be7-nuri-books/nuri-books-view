package shop.nuribooks.view.exception;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException() {
		super("페이지에 접근할 권한이 없습니다.");
	}
}

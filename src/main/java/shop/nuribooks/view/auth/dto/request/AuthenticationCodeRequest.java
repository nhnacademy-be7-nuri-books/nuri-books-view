package shop.nuribooks.view.auth.dto.request;

public record AuthenticationCodeRequest(String username, String hookUrl) {
}

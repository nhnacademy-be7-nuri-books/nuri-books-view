package shop.nuribooks.view.common.sender;

import shop.nuribooks.view.auth.dto.request.AuthenticationCodeRequest;

public interface MessageSender {
	String sendMessage(AuthenticationCodeRequest request, MessageRequest message);
}

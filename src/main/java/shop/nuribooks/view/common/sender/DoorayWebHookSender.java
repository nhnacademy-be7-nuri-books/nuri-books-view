package shop.nuribooks.view.common.sender;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import shop.nuribooks.view.auth.dto.request.AuthenticationCodeRequest;
import shop.nuribooks.view.exception.BadRequestException;
import shop.nuribooks.view.exception.DefaultServerError;

import java.nio.charset.StandardCharsets;

@Component
public class DoorayWebHookSender implements MessageSender {

    @Override
    public String sendMessage(AuthenticationCodeRequest request, MessageRequest message) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(request.hookUrl());
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");

            HookBody hookBody = new HookBody(message.subject(), message.message());
            Gson gson = new Gson();
            StringEntity stringEntity = new StringEntity(gson.toJson(hookBody), StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);

            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                throw new BadRequestException("서버 오류입니다. 다시 시도하세요.");
            }
            // 인증번호 발송 성공
            return "인증번호가 발송되었습니다.";

        } catch (Exception e) {
            throw new DefaultServerError(500, "서버 오류입니다. 다시 시도하세요.");
        }
    }

    public static class HookBody {
        String botName;
        String botIconImage;
        String text;

        public HookBody(String botName, String text) {
            this.botName = botName;
            this.botIconImage = "https://static.dooray.com/static_images/dooray-bot.png";
            this.text = text;
        }
    }
}

package shop.nuribooks.view.oauth.dto;

import java.util.List;
import java.util.Map;


public record OAuth2ResultResponse(Map<String, List<String>> responseMap, String status) {
}

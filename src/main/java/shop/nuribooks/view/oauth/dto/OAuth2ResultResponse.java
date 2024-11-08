package shop.nuribooks.view.oauth.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ResultResponse {
	private Map<String, List<String>> responseMap;
	private String status;
}

package shop.nuribooks.view.common.advice;

import java.util.Optional;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute("currentUri")
	String getCurrentUri(HttpServletRequest req) {
		return req.getRequestURI();
	}

	@ModelAttribute("currentQuery")
	String getCurrentQuery(HttpServletRequest req) {
		return Optional.ofNullable(req.getQueryString()).orElse("");
	}
}

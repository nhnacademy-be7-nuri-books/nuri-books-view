package shop.nuribooks.view.common.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.exception.CustomJsonProcessingException;
import shop.nuribooks.view.exception.DefaultServerError;

/**
 * 공동 예외 처리
 *
 *  <p>
 *      에러 페이지로 넘겨야 할 예외를 처리한다.
 *  </p>
 *
 * @author nuri
 */
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

	/**
	 * 서버 예외가 발생할 시 처리
	 *
	 *  <p>
	 *  	예외 발생 시 GET /error 로 리다이렉트
	 *  </p>
	 *
	 * @param ex {@link DefaultServerError} 예외
	 * @param redirectAttributes 예외 메세지 전달하기 위한 속성
	 * @return 리다이렉트 할 URL (/error)
	 */
	@ExceptionHandler({DefaultServerError.class})
	public String handlerDefaultServerError(DefaultServerError ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(ex.getMessage());
		return "redirect:/error";
	}

	/**
	 * Json 파싱 예외가 발생할 시 처리
	 *
	 *  <p>
	 *  	예외 발생 시 GET /error 로 리다이렉트
	 *  </p>
	 *
	 * @param ex {@link CustomJsonProcessingException} 예외
	 * @param redirectAttributes 예외 메세지 전달하기 위한 속성
	 * @return 리다이렉트 할 URL (/error)
	 */
	@ExceptionHandler({CustomJsonProcessingException.class})
	public String handlerDefaultServerError(CustomJsonProcessingException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(ex.getMessage());
		return "redirect:/error";
	}

}

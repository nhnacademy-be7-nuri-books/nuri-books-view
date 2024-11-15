package shop.nuribooks.view.common.advice;

import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.dto.ErrorResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.BadRequestException;
import shop.nuribooks.view.exception.CustomJsonProcessingException;
import shop.nuribooks.view.exception.DefaultServerError;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;
import shop.nuribooks.view.exception.ResourceNotFoundException;
import shop.nuribooks.view.exception.UnauthorizedException;

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

	@Value("${error.message-key}")
	private String errorMessageKey;

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

	@ExceptionHandler({ResourceAlreadyExistsException.class})
	public String handlerPublisherAlreadyExists(ResourceAlreadyExistsException ex,
		RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
		return "redirect:/error";
	}

	/**
	 * 권한 없는 예외가 발생할 시 처리
	 *
	 * @param ex {@link UnauthorizedException}
	 * @param redirectAttributes 예외 메세지 전달하기 위한 속성
	 * @return 리다이렉트 할 URL (/error)
	 */
	@ExceptionHandler({UnauthorizedException.class})
	public String handlerUnauthorized(UnauthorizedException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(ex.getMessage());
		return "redirect:/error";
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public String handlerResourceNotFound(ResourceAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(ex.getMessage());
		return "redirect:/error";
	}

	@ExceptionHandler({BadRequestException.class})
	public String handlerBadRequest(BadRequestException ex, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(errorMessageKey, ex.getMessage());
		return "redirect:/error";
	}

	@ExceptionHandler({FeignException.class})
	public ResponseEntity<ResponseMessage> feignExceptionHandler(FeignException ex, HttpServletResponse response) throws
		IOException {
		if(ex.status() >= 500){
			log.error(ex.getMessage());
		}
		else if(ex.status() == 401){
			response.sendRedirect("/logout");
		}
		return ResponseEntity.status(ex.status()).body(new ResponseMessage(ex.status(), ExceptionUtil.handleFeignException(ex)));
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ResponseMessage> validFailHandler(MethodArgumentNotValidException ex){
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.joining(", "));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), errorMessage));
	}

}

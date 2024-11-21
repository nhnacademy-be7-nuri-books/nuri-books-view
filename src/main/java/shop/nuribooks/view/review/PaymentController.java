package shop.nuribooks.view.review;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PaymentController {

	/**
	 * 인증성공처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public void paymentRequest(HttpServletRequest request, Model model) throws Exception {

		// todo : 결제 인증 성공 처리 - AMOUNT 가 서버에서 저장된 AMOUNT와 같은 지 확인 하기
		// /success?paymentType={PAYMENT_TYPE}&orderId={ORDER_ID}
		// &paymentKey={PAYMENT_KEY}&amount={AMOUNT}
		log.debug("결제 성공");

		//return "/success";
	}

	/**
	 * 인증실패처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fail", method = RequestMethod.GET)
	public void failPayment(HttpServletRequest request, Model model) throws Exception {

		// todo : 결제 인증 실패 처리

		// String failCode = request.getParameter("code");
		// String failMessage = request.getParameter("message");
		//
		// model.addAttribute("code", failCode);
		// model.addAttribute("message", failMessage);

		//return "/fail";
		log.debug("결제 실패");
	}
}

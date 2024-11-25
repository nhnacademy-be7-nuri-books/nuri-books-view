package shop.nuribooks.view.order.returnrequest.service;

import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;

public interface ReturnRequestService {

	void registerReturnRequest(Long orderId, ReturnRequestRegisterRequest returnRequestRegisterRequest);

	String uploadImage(MultipartFile file);
}

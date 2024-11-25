package shop.nuribooks.view.order.returnrequest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.dto.response.ReturnRequestResponse;

public interface ReturnRequestService {

	void registerReturnRequest(Long orderId, ReturnRequestRegisterRequest returnRequestRegisterRequest);

	Page<ReturnRequestResponse> getReturnRequests(Pageable pageable);

	String uploadImage(MultipartFile file);

}

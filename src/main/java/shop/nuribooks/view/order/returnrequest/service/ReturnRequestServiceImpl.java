package shop.nuribooks.view.order.returnrequest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.dto.response.ReturnRequestResponse;
import shop.nuribooks.view.order.returnrequest.feign.ReturnRequestServiceClient;

@RequiredArgsConstructor
@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

	private final ReturnRequestServiceClient returnRequestServiceClient;
	private final BookServiceClient bookServiceClient;

	@Override
	public void registerReturnRequest(Long orderId, ReturnRequestRegisterRequest returnRequestRegisterRequest) {
		returnRequestServiceClient.registerReturnRequest(orderId, returnRequestRegisterRequest);
	}

	@Override
	public Page<ReturnRequestResponse> getReturnRequests(Pageable pageable) {
		return returnRequestServiceClient.getReturnRequests(pageable).getBody();
	}

	@Override
	public String uploadImage(MultipartFile file) {
		return bookServiceClient.uploadImage(file);
	}
}

package shop.nuribooks.view.order.returnrequest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.feign.ReturnRequestServiceClient;

@RequiredArgsConstructor
@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

	private final ReturnRequestServiceClient returnRequestServiceClient;
	private final BookServiceClient bookServiceClient;

	@Override
	public void registerReturnRequest(Long orderId, ReturnRequestRegisterRequest returnRequestRegisterRequest) {

	}

	@Override
	public String uploadImage(MultipartFile file) {
		return bookServiceClient.uploadImage(file);
	}
}

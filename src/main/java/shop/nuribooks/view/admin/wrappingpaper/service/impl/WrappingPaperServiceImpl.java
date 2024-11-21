package shop.nuribooks.view.admin.wrappingpaper.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.admin.wrappingpaper.feign.WrappingPaperServiceFeignClient;
import shop.nuribooks.view.admin.wrappingpaper.service.WrappingPaperService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@RequiredArgsConstructor
@Service
public class WrappingPaperServiceImpl implements WrappingPaperService {
	private final WrappingPaperServiceFeignClient wrappingPaperServiceFeignClient;

	@Override
	public Page<WrappingPaperResponse> getWrappingPapers(Pageable pageable) {
		return wrappingPaperServiceFeignClient.getWrappingPapers(pageable).getBody();
	}

	@Override
	public ResponseMessage registerWrappingPaper(WrappingPaperRequest wrappingPaperRequest) {
		return wrappingPaperServiceFeignClient.registerWrappingPaper(wrappingPaperRequest).getBody();
	}

	@Override
	public ResponseMessage updateWrappingPaper(Long id, WrappingPaperRequest wrappingPaperRequest) {
		return wrappingPaperServiceFeignClient.updateWrappingPaper(wrappingPaperRequest, id).getBody();
	}

	@Override
	public ResponseMessage deleteWrappingPaper(Long id) {
		return wrappingPaperServiceFeignClient.removeWrappingPaper(id).getBody();
	}
}

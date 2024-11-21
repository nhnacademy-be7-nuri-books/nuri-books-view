package shop.nuribooks.view.admin.wrappingpaper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperRequest;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface WrappingPaperService {
	Page<WrappingPaperResponse> getWrappingPapers(Pageable pageable);

	ResponseMessage registerWrappingPaper(WrappingPaperRequest wrappingPaperRequest);

	ResponseMessage updateWrappingPaper(Long id, WrappingPaperRequest wrappingPaperRequest);

	ResponseMessage deleteWrappingPaper(Long id);
}

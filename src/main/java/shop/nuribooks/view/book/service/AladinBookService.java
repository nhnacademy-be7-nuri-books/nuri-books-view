package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;
import shop.nuribooks.view.book.dto.BaseBookRegisterRequest;

public interface AladinBookService {
	List<AladinBookListItemResponse> getAladinBookList(String queryType, String searchTarget, int maxResults);
	AladinBookListItemResponse getAladinBookByIsbn(String isbn);
}

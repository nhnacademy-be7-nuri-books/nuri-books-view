package shop.nuribooks.view.book.service;

import java.util.List;

import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;

public interface AladinBookService {
	List<AladinBookListItemResponse> getAladinBookList();
	AladinBookListItemResponse getAladinBookByIsbn(String isbn);
	void saveAladinBook(AladinBookSaveRequest saveRequest);
}

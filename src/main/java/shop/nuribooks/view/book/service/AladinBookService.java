package shop.nuribooks.view.book.service;

import java.util.List;

import shop.nuribooks.view.book.dto.AladinBookListItemResponse;

public interface AladinBookService {
	List<AladinBookListItemResponse> getAladinBookList();
	AladinBookListItemResponse getAladinBookByIsbn(String isbn);
}

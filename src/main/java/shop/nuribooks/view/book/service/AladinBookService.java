package shop.nuribooks.view.book.service;

import java.util.List;

import shop.nuribooks.view.book.dto.AladinBookListItemResponse;

public interface AladinBookService {
	List<AladinBookListItemResponse> getAladinBookList(String queryType, String searchTarget, int maxResults);
	AladinBookListItemResponse getAladinBookByIsbn(String isbn);
}

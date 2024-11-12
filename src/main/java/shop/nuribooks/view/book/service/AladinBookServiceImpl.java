package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.feign.AladinBookServiceClient;

@RequiredArgsConstructor
@Service
public class AladinBookServiceImpl implements AladinBookService{
	private final AladinBookServiceClient aladinBookServiceClient;

	@Override
	public List<AladinBookListItemResponse> getAladinBookList(String queryType, String searchTarget, int maxResults) {
		return aladinBookServiceClient.getAladinBookList(queryType, searchTarget, maxResults).getBody();
	}

	@Override
	public AladinBookListItemResponse getAladinBookByIsbn(String isbn) {
		return aladinBookServiceClient.getBookByIsbnWithAladin(isbn).getBody();
	}
}

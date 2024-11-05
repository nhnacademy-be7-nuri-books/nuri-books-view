package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;
import shop.nuribooks.view.book.feign.AladinBookServiceClient;

@RequiredArgsConstructor
@Service
public class AladinBookServiceImpl implements AladinBookService{
	private final AladinBookServiceClient aladinBookServiceClient;

	@Override
	public List<AladinBookListItemResponse> getAladinBookList() {
		List<AladinBookListItemResponse> response = aladinBookServiceClient.getAladinBookList().getBody();
		if (response != null) {
			return response;
		}
		return List.of();
	}

	@Override
	public AladinBookListItemResponse getAladinBookByIsbn(String isbn) {
		return aladinBookServiceClient.getBookByIsbn(isbn).getBody();
	}

	@Override
	public void saveAladinBook(AladinBookSaveRequest saveRequest) {
		aladinBookServiceClient.saveAladinBook(saveRequest);
	}
}

package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookListResponse;
import shop.nuribooks.view.book.feign.AladinBookServiceClient;

@RequiredArgsConstructor
@Service
public class AladinBookServiceImpl implements AladinBookService{
	private final AladinBookServiceClient aladinBookServiceClient;

	@Override
	public List<AladinBookListItemResponse> getAladinBookList() {
		AladinBookListResponse response = aladinBookServiceClient.getAladinBookList().getBody();
		if(response != null && response.item() != null) {
			return response.item();
		}
		return List.of();
	}
}

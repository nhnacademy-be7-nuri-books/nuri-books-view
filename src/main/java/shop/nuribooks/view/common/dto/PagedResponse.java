package shop.nuribooks.view.common.dto;

import java.util.List;

public record PagedResponse<T>(
	List<T> content, //도서 목록 데이터
	int page, //현재 페이지
	int size, //페이지 크기
	int totalPages, //전체 페이지 수
	long totalElements //전체 요소 수
) {
}

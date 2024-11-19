package shop.nuribooks.view.admin.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.admin.grade.dto.GradeDetailsResponse;
import shop.nuribooks.view.admin.grade.dto.GradeListResponse;
import shop.nuribooks.view.admin.grade.dto.GradeRegisterRequest;
import shop.nuribooks.view.admin.grade.dto.GradeUpdateRequest;
import shop.nuribooks.view.admin.grade.feign.AdminGradeServiceClient;

@Service
@RequiredArgsConstructor
public class AdminGradeServiceImpl implements AdminGradeService {

	private final AdminGradeServiceClient gradeServiceClient;

	@Override
	public List<GradeListResponse> getAllGrades() {

		return gradeServiceClient.getGradeList().getBody();
	}

	@Override
	public ResponseMessage registerGrade(GradeRegisterRequest request) {

		return gradeServiceClient.registerGrade(request).getBody();
	}

	@Override
	public GradeDetailsResponse getGradeDetails(String name) {

		return gradeServiceClient.getGradeDetails(name).getBody();
	}

	@Override
	public String updateGrade(String name, GradeUpdateRequest request) {

		try {
			return gradeServiceClient.updateGrade(name, request).getBody().message();
		} catch (FeignException e) {
			return extractMessage(e.getMessage());
		}
	}

	@Override
	public String deleteGrade(String name) {

		try {
			return gradeServiceClient.deleteGrade(name).getBody().message();
		} catch (FeignException e) {
			return extractMessage(e.getMessage());
		}
	}


	/**
	 * json 형식의 response에서 message의 value만을 추출
	 */
	private static String extractMessage(String jsonResponse) {
		// "message"라는 키를 찾고, 해당 값 추출
		int messageStartIndex = jsonResponse.indexOf("\"message\":") + 11; // "message": 의 뒤부터 시작
		int messageEndIndex = jsonResponse.indexOf("\"", messageStartIndex); // " 이후로 끝

		if (!jsonResponse.contains("\"message\":")) {
			return "메시지 추출 실패";  // message가 없거나 JSON 형식이 이상한 경우
		}

		// "message" 값 추출
		return jsonResponse.substring(messageStartIndex, messageEndIndex);
	}
}

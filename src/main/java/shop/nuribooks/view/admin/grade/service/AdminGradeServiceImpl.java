package shop.nuribooks.view.admin.grade.service;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	public ResponseMessage updateGrade(String name, GradeUpdateRequest request) {

		return gradeServiceClient.updateGrade(name, request).getBody();
	}

	@Override
	public String deleteGrade(String name) {

		ResponseEntity<ResponseMessage> response = gradeServiceClient.deleteGrade(name);

		return switch (response.getStatusCode()) {
			case NO_CONTENT -> "등급이 성공적으로 삭제되었습니다.";
			case CONFLICT -> "해당 등급을 가진 회원이 존재하여 삭제할 수 없습니다.";
			default -> "서버 오류로 인해 요청을 처리하지 못했습니다. 잠시 후에 다시 시도하십시오.";
		};
	}

}

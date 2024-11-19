package shop.nuribooks.view.admin.grade.service;

import java.util.List;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.admin.grade.dto.GradeDetailsResponse;
import shop.nuribooks.view.admin.grade.dto.GradeListResponse;
import shop.nuribooks.view.admin.grade.dto.GradeRegisterRequest;
import shop.nuribooks.view.admin.grade.dto.GradeUpdateRequest;

public interface AdminGradeService {

	List<GradeListResponse> getAllGrades();

	ResponseMessage registerGrade(GradeRegisterRequest request);

	GradeDetailsResponse getGradeDetails(String name);

	String updateGrade(String name, GradeUpdateRequest request);

	String deleteGrade(String name);
}

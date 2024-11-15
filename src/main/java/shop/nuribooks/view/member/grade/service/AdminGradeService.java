package shop.nuribooks.view.member.grade.service;

import java.util.List;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.member.grade.dto.GradeDetailsResponse;
import shop.nuribooks.view.member.grade.dto.GradeListResponse;
import shop.nuribooks.view.member.grade.dto.GradeRegisterRequest;
import shop.nuribooks.view.member.grade.dto.GradeUpdateRequest;

public interface AdminGradeService {

	List<GradeListResponse> getAllGrades();

	ResponseMessage registerGrade(GradeRegisterRequest request);

	GradeDetailsResponse getGradeDetails(String name);

	ResponseMessage updateGrade(String name, GradeUpdateRequest request);

	String deleteGrade(String name);
}

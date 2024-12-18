package shop.nuribooks.view.admin.batch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/batch")
public class AdminBatchController {

	private final AdminBatchClient adminBatchClient;

	@GetMapping
	public String showBatchList() {
		return "admin/batch/batch-list";
	}

	@GetMapping("/inactive")
	public String inactiveMembersJob() {
		adminBatchClient.inactiveMembersJob();

		return "admin/batch/batch-list";
	}

	@GetMapping("/withdraw")
	public String softDeleteMembersJob() {
		adminBatchClient.softDeleteMembersJob();

		return "admin/batch/batch-list";
	}

	@GetMapping("/grade")
	public String membersGradeUpdateJob() {
		adminBatchClient.membersGradeUpdateJob();

		return "admin/batch/batch-list";
	}

	@GetMapping("/birthday")
	public String issueBirthdayCouponJob() {
		adminBatchClient.issueBirthdayCouponJob();

		return "admin/batch/batch-list";
	}
}

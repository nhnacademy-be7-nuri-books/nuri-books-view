package shop.nuribooks.view.admin.batch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "batch", url = "http://localhost:8085")
public interface AdminBatchClient {

    @GetMapping("/api/batch/inactive")
    ResponseEntity<Void> inactiveMembersJob();

    @GetMapping("/api/batch/withdraw")
    ResponseEntity<Void> softDeleteMembersJob();

    @GetMapping("/api/batch/grade")
    ResponseEntity<Void> membersGradeUpdateJob();
}

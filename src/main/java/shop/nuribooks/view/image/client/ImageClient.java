package shop.nuribooks.view.image.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image", url = "http://localhost:8080")
public interface ImageClient {
	@PostMapping(value = "/api/image/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	List<String> uploadImage(@RequestPart("files") List<MultipartFile> files);
}

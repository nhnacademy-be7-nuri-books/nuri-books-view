package shop.nuribooks.view.image.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.image.client.ImageClient;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {
	private final ImageClient imageClient;

	@PostMapping(value = "/api/image/multi")
	public ResponseEntity<List<String>> uploadMultiImage(@RequestPart("files") List<MultipartFile> files){
		List<String> result = imageClient.uploadImage(files);
		ResponseEntity re = ResponseEntity.status(HttpStatus.CREATED).body(result);
		return re;
	}
}

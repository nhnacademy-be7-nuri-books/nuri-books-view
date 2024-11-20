package shop.nuribooks.view.admin.publisher.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.admin.publisher.service.PublisherService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/publisher")
public class PublisherController {
	private final PublisherService publisherService;

	@GetMapping
	public String showRegisterPublisherForm(@PageableDefault Pageable pageable, Model model) {
		Page<PublisherResponse> publishers = publisherService.getAllPublishers(pageable);
		model.addAttribute("pages", publishers);
		return "admin/publisher/publisher";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerPublisher(PublisherRequest publisherRequest) {
		ResponseMessage message = publisherService.registerPublisher(publisherRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{publisher-id}")
	public ResponseEntity<ResponseMessage> updatePublisher(@PathVariable("publisher-id") Long id,
		@Valid @ModelAttribute PublisherRequest publisherRequest) {
		ResponseMessage message = publisherService.updatePublisher(id, publisherRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@DeleteMapping("/{publisher-id}")
	public ResponseEntity<ResponseMessage> deletePublisher(@PathVariable("publisher-id") Long id) {
		ResponseMessage message = publisherService.deletePublisher(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}
}

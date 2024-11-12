package shop.nuribooks.view.admin.publisher.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.admin.publisher.dto.PublisherRequest;
import shop.nuribooks.view.admin.publisher.dto.PublisherResponse;
import shop.nuribooks.view.admin.publisher.service.PublisherService;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/publisher")
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public String showPublisherList(Model model) {
        List<PublisherResponse> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        return "admin/publisher/publisher-list";
    }

    @GetMapping("/register")
    public String showPublisherRegisterPage() {
        return "admin/publisher/publisher-register";
    }

    @PostMapping
    public String registerPublisher(PublisherRequest publisherRequest) {
        publisherService.registerPublisher(publisherRequest);
        return "redirect:/admin/publisher";
    }

    @GetMapping("/{publisher-id}")
    public String showEditPublisherPage(@PathVariable("publisher-id") Long id, Model model) {
        PublisherResponse publisher = publisherService.getPublisher(id);
        model.addAttribute("publisher", publisher);
        return "admin/publisher/publisher-edit";
    }

    @PutMapping("/{publisher-id}")
    public String updatePublisher(@PathVariable("publisher-id") Long id, PublisherRequest publisherRequest) {
        publisherService.updatePublisher(id, publisherRequest);
        return "redirect:/admin/publisher";
    }

    @DeleteMapping("/{publisher-id}")
    public String deletePublisher(@PathVariable("publisher-id") Long id) {
        publisherService.deletePublisher(id);
        return "redirect:/admin/publisher";
    }
}

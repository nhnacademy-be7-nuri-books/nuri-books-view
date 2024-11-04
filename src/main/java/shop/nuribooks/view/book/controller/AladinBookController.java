package shop.nuribooks.view.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.service.AladinBookService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/view/aladin")
public class AladinBookController {
	private final AladinBookService aladinBookService;

	@GetMapping("/books")
	public String getAladinBookList(Model model) {
		List<AladinBookListItemResponse> books = aladinBookService.getAladinBookList();
		model.addAttribute("books", books);
		return "aladinList";
	}
}

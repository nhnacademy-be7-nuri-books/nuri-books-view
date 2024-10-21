package shop.nuribooks.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/view")
public class DemoController {

	@GetMapping("/index")
	public String demo() {
		return "index";
	}
}

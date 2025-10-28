package com.queen.adapters.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatastarController {

	@GetMapping("/datastar")
	public String datastar() {
		return "datastar";
	}
}

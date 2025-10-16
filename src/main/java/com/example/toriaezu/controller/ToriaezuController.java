package com.example.toriaezu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToriaezuController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
}


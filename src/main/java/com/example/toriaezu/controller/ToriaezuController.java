package com.example.toriaezu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.toriaezu.model.TestDTO;

@Controller
public class ToriaezuController {

    // 初期表示（GET）
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("test", "テスト表示");
        model.addAttribute("inputData", null);
        return "index";
    }


    // フォーム送信（POST）
    @PostMapping("/input")
    public String inputSample(@ModelAttribute("inputTest") TestDTO inputData, Model model) {
        model.addAttribute("test", "テスト表示");
        model.addAttribute("inputData", inputData.getName());
        model.addAttribute("events", List.of(inputData));
        return "index";
    }

}

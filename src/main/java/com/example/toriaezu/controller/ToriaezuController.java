package com.example.toriaezu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.toriaezu.model.TestDTO;
import com.example.toriaezu.model.entity.EventEntity;
import com.example.toriaezu.model.repository.EventRepository;

@Controller
public class ToriaezuController {
    @Autowired
    EventRepository eventRepository;
    // 初期表示（GET）
    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("test", "テスト表示");
        model.addAttribute("inputData", null);
        List<EventEntity> eventEntitiesList = eventRepository.findAll();
        model.addAttribute("inputTest", new TestDTO());
        List<TestDTO> testDTOList = new ArrayList<>();
        for (EventEntity eventEntities : eventEntitiesList) {
            TestDTO event = new TestDTO();
            event.setTitle(eventEntities.getTitle());
            event.setDate(eventEntities.getDate());
            event.setName(eventEntities.getName());
            event.setDetail(eventEntities.getDetail());
            testDTOList.add(event);
        }
        model.addAttribute("events", testDTOList);
        return "index";
    }


    // フォーム送信（POST）
    @PostMapping("/input")
    public String inputSample(@ModelAttribute("inputTest") TestDTO inputData, Model model) {
//        model.addAttribute("test", "テスト表示");
//        model.addAttribute("inputData", inputData.getName());
        model.addAttribute("events", List.of(inputData));
        EventEntity event = new EventEntity();
        event.setTitle(inputData.getTitle());
        event.setDate(inputData.getDate());
        event.setName(inputData.getName());
        event.setDetail(inputData.getDetail());
        eventRepository.save(event);
        List<EventEntity> eventEntitiesList = eventRepository.findAll();
        model.addAttribute("inputTest", new TestDTO());
        List<TestDTO> testDTOList = new ArrayList<>();
        for (EventEntity eventEntities : eventEntitiesList) {
            TestDTO eventdto = new TestDTO();
            eventdto.setTitle(eventEntities.getTitle());
            eventdto.setDate(eventEntities.getDate());
            eventdto.setName(eventEntities.getName());
            eventdto.setDetail(eventEntities.getDetail());
            testDTOList.add(eventdto);
        }
        model.addAttribute("events", testDTOList);
        return "index";
    }

}

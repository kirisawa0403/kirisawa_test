package com.example.toriaezu.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("inputData", null);
        List<EventEntity> eventEntitiesList = eventRepository.findAll();
        model.addAttribute("inputTest", new TestDTO());
        List<TestDTO> testDTOList = new ArrayList<>();
        for (EventEntity eventEntities : eventEntitiesList) {
            TestDTO event = new TestDTO();
            event.setId(eventEntities.getId());
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
    public String addEvent(@ModelAttribute("inputTest") @Valid TestDTO inputData,
                           BindingResult bindingResult,
                           Model model) {

        // バリデーションエラーがある場合
        if (bindingResult.hasErrors()) {
            // 既存イベントも再表示
            List<EventEntity> eventEntitiesList = eventRepository.findAll();
            List<TestDTO> testDTOList = new ArrayList<>();
            for (EventEntity eventEntities : eventEntitiesList) {
                TestDTO eventdto = new TestDTO();
                eventdto.setId(eventEntities.getId());
                eventdto.setTitle(eventEntities.getTitle());
                eventdto.setDate(eventEntities.getDate());
                eventdto.setName(eventEntities.getName());
                eventdto.setDetail(eventEntities.getDetail());
                testDTOList.add(eventdto);
            }
            model.addAttribute("events", testDTOList);

            return "index"; // フォームに戻す
        }

        // 正常処理
        EventEntity event = new EventEntity();
        event.setTitle(inputData.getTitle());
        event.setDate(inputData.getDate());
        event.setName(inputData.getName());
        event.setDetail(inputData.getDetail());
        eventRepository.save(event);

        return "redirect:/";
    }
    
    @PostMapping("/update")
    public String updateEvent(@ModelAttribute("inputTest") TestDTO updatedEvent) {
        if(updatedEvent.getId() != null) {
            EventEntity event = eventRepository.findById(updatedEvent.getId()).orElse(null);
            if (event != null) {
                event.setTitle(updatedEvent.getTitle());
                event.setDate(updatedEvent.getDate());
                event.setName(updatedEvent.getName());
                event.setDetail(updatedEvent.getDetail());
                eventRepository.save(event);
            }
        }
        return "redirect:/";
    }
    
    @PostMapping("/delete")
    public String deleteEvent(long id) {
        eventRepository.deleteById(id);
        return "redirect:/";
    }
}

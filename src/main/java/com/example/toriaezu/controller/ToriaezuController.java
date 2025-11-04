package com.example.toriaezu.controller;

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

    /** 共通：Entity → DTO 変換 */
    private TestDTO convertToDTO(EventEntity entity) {
        TestDTO dto = new TestDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDate(entity.getDate());
        dto.setName(entity.getName());
        dto.setDetail(entity.getDetail());
        return dto;
    }

    /** 共通：登録済みイベント一覧DTO取得 */
    private List<TestDTO> getAllEventsAsDTO() {
        return eventRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // 初期表示（GET）
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("inputTest", new TestDTO());
        model.addAttribute("events", getAllEventsAsDTO());
        return "index";
    }

    // 登録（POST）
    @PostMapping("/input")
    public String addEvent(@ModelAttribute("inputTest") @Valid TestDTO inputData,
                           BindingResult bindingResult,
                           Model model) {

        // バリデーションエラー時
        if (bindingResult.hasErrors()) {
            model.addAttribute("events", getAllEventsAsDTO());
            return "index";
        }

        EventEntity event = new EventEntity();
        event.setTitle(inputData.getTitle());
        event.setDate(inputData.getDate());
        event.setName(inputData.getName());
        event.setDetail(inputData.getDetail());
        eventRepository.save(event);

        return "redirect:/";
    }

    // 更新（POST）
    @PostMapping("/update")
    public String updateEvent(@ModelAttribute("inputTest") TestDTO updatedEvent) {
        if (updatedEvent.getId() != null) {
            eventRepository.findById(updatedEvent.getId()).ifPresent(event -> {
                event.setTitle(updatedEvent.getTitle());
                event.setDate(updatedEvent.getDate());
                event.setName(updatedEvent.getName());
                event.setDetail(updatedEvent.getDetail());
                eventRepository.save(event);
            });
        }
        return "redirect:/";
    }

    // 削除（POST）
    @PostMapping("/delete")
    public String deleteEvent(long id) {
        eventRepository.deleteById(id);
        return "redirect:/";
    }
}

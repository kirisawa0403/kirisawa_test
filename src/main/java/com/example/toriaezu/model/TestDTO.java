package com.example.toriaezu.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TestDTO {
    private Long id;

    @NotBlank(message = "予定名を入力してください")
    private String title;

    @NotNull(message = "日付を入力してください")
    private LocalDate date;

    @NotBlank(message = "名前を入力してください")
    private String name;

    @NotBlank(message = "詳細を入力してください")
    private String detail;
}

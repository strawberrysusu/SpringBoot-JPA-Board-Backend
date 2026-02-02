package com.example.practicebackend1.dto;


import com.example.practicebackend1.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BoardDto {
    private String title;
    private String content;




}

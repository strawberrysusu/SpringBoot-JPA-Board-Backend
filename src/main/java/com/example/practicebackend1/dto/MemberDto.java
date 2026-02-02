package com.example.practicebackend1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String email;
    private String password;
    private String nickname;
}

package com.example.practicebackend1.controller;

import com.example.practicebackend1.dto.MemberDto;
import com.example.practicebackend1.entity.Member;
import com.example.practicebackend1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService service;

    // 회원가입 (POST /api/members/signup)
    @PostMapping("/signup")
    public Member signup(@RequestBody MemberDto dto) {
        return service.signup(dto);
    }
    // 로그인 (POST /api/members/login)
    @PostMapping("/login")
    public Member login(@RequestBody MemberDto dto) {
        // dto에 있는 email, password만 꺼내서 확인
        return service.login(dto.getEmail(), dto.getPassword());
    }
}
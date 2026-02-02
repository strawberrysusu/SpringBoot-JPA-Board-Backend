package com.example.practicebackend1.service;

import com.example.practicebackend1.dto.MemberDto;
import com.example.practicebackend1.entity.Member;
import com.example.practicebackend1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    // 회원가입
    public Member signup(MemberDto dto) {
        // 1. 혹시 이미 있는 아이디냐? (중복 검사)
        if(repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 있는 아이디입니다!");
        }

        // 2. 없으면 저장! (원래는 비밀번호 암호화해야 하는데, 지금은 그냥 저장)
        Member member = new Member(null, dto.getEmail(), dto.getPassword(), dto.getNickname());
        return repository.save(member);
    }
    // 로그인 (아이디, 비번 맞는지 확인)
    public Member login(String email, String password) {
        // 1. 아이디로 조회
        Member member = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("그런 아이디 없는데요?"));

        // 2. 비밀번호 맞는지 확인
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호 틀림!");
        }

        // 3. 통과하면 회원 정보 리턴
        return member;
    }
}
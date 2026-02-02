package com.example.practicebackend1.repository;

import com.example.practicebackend1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 🔍 아이디(이메일)로 회원 찾기
    Optional<Member> findByEmail(String email);
}

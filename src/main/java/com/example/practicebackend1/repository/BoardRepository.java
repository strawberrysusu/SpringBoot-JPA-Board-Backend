package com.example.practicebackend1.repository;

import com.example.practicebackend1.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

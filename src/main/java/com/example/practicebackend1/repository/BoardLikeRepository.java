package com.example.practicebackend1.repository;

import com.example.practicebackend1.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    boolean existsByBoardIdAndMemberId(Long boardId, Long memberId);
    // 👇 [추가] 2. 그 장부 좀 가져와봐 (삭제하려고 찾음)
    BoardLike findByBoardIdAndMemberId(Long boardId, Long memberId);
}

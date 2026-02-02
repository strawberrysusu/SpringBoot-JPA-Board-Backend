package com.example.practicebackend1.repository;

import com.example.practicebackend1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 🔍 특정 게시글(boardId)에 달린 댓글들만 찾아줘!
    List<Comment> findByBoardId(Long boardId);
}
package com.example.practicebackend1.repository;

import com.example.practicebackend1.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository  extends JpaRepository<CommentLike,Long> {

    CommentLike findByCommentIdAndMemberId(Long commentId, Long memberId);
}

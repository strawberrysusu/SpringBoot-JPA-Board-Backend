package com.example.practicebackend1.service;

import com.example.practicebackend1.dto.BoardDto;
import com.example.practicebackend1.dto.CommentDto;
import com.example.practicebackend1.entity.*;
import com.example.practicebackend1.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired private BoardRepository repository;
    @Autowired private MemberRepository memberRepository; // [필수] 회원 조회용
    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardLikeRepository boardLikeRepository;
    @Autowired private CommentLikeRepository commentLikeRepository;

    // 1. 목록 조회
    public List<Board> getAllBoards(){
        return repository.findAll();
    }

    // 2. 글 상세 조회
    public Board getBoard(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));
    }

    // [핵심 수정] 3. 글 작성 (회원 정보 연결)
    public Board createBoard(BoardDto dto, Long memberId) {
        // (1) 회원 조회: 없는 회원이면 에러 냄 (로그인 검증 역할)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 로그인 해주세요!"));

        // (2) 게시글 생성: Dto의 String writer는 무시하고, 진짜 Member 객체를 넣음
        // (Board 엔티티 생성자가 수정되어 있어야 함!)
        Board board = new Board(null, dto.getTitle(), dto.getContent(), member, 0);

        // (3) 저장
        return repository.save(board);
    }

    // 4. 글 삭제
    public void deleteBoard(Long id){
        repository.deleteById(id);
    }

    // 5. 게시글 좋아요 (토글)
    @Transactional
    public Board clickLike(Long boardId, Long memberId) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 좋아요 여부 확인 (토글)
        if(boardLikeRepository.existsByBoardIdAndMemberId(boardId, memberId)){
            // 이미 눌렀으면 취소
            BoardLike like = boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId);
            boardLikeRepository.delete(like);
            board.decreaseLike();
        } else {
            // 안 눌렀으면 추가
            BoardLike like = new BoardLike(member, board);
            boardLikeRepository.save(like);
            board.increaseLike();
        }
        return board;
    }

    // [핵심 수정] 6. 댓글 작성 (회원 정보 연결)
    public Comment writeComment(Long boardId, CommentDto dto, Long memberId) {
        // (1) 게시글 조회
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // (2) 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("로그인 먼저 해주세요."));

        // (3) 댓글 생성 (Comment 엔티티 생성자도 Member를 받도록 수정되어 있어야 함!)
        Comment comment = new Comment(dto.getContent(), member, board);

        return commentRepository.save(comment);
    }

    // 7. 댓글 목록 조회
    public List<Comment> getComments(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    // 8. 댓글 좋아요 (토글)
    @Transactional
    public Comment clickCommentLike(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        CommentLike existingLike = commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId);

        if (existingLike != null) {
            commentLikeRepository.delete(existingLike);
            comment.decreaseLike();
        } else {
            CommentLike like = new CommentLike(member, comment);
            commentLikeRepository.save(like);
            comment.increaseLike();
        }
        return comment; // 변경된 댓글 정보 반환
    }
}
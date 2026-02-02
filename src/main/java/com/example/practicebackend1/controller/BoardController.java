package com.example.practicebackend1.controller;

import com.example.practicebackend1.dto.BoardDto;
import com.example.practicebackend1.dto.CommentDto;
import com.example.practicebackend1.entity.Board;
import com.example.practicebackend1.entity.Comment;
import com.example.practicebackend1.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService service;

    // 1. 글 목록 조회 (GET /api/boards)
    @GetMapping
    public List<Board> list(){
        return service.getAllBoards();
    }

    // 2. 글 쓰기 (POST /api/boards?memberId=1)
    // [수정] 이제 로그인한 사람(memberId) 정보를 같이 받아야 함!
    @PostMapping
    public Board write(@RequestBody BoardDto dto, @RequestParam Long memberId){
        return service.createBoard(dto, memberId);
    }

    // 3. 상세 조회 (GET /api/boards/1)
    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Long id) {
        return service.getBoard(id);
    }

    // 4. 글 삭제 (DELETE /api/boards/1)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteBoard(id);
    }

    // 5. 게시글 좋아요 (POST /api/boards/1/like?memberId=1)
    @PostMapping("/{id}/like")
    public Board like(@PathVariable Long id, @RequestParam Long memberId){
        return service.clickLike(id, memberId);
    }

    // 6. 댓글 쓰기 (POST /api/boards/1/comments?memberId=1)
    // [수정] 댓글도 누가 썼는지 알아야 하니까 memberId 받음
    @PostMapping("/{id}/comments")
    public Comment writeComment(@PathVariable Long id,
                                @RequestBody CommentDto dto,
                                @RequestParam Long memberId){
        return service.writeComment(id, dto, memberId);
    }

    // 7. 댓글 조회 (GET /api/boards/1/comments)
    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id){
        return service.getComments(id);
    }

    // 8. 댓글 좋아요 (POST /api/boards/comments/1/like?memberId=1)
    // (URL 구조를 살짝 다듬음: 댓글 ID가 유니크하니까 boards/{id} 굳이 안 타도 됨)
    @PostMapping("/comments/{commentId}/like")
    public Comment likeComment(@PathVariable Long commentId, @RequestParam Long memberId){
        return service.clickCommentLike(commentId, memberId);
    }
}
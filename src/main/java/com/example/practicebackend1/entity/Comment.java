package com.example.practicebackend1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter

@NoArgsConstructor


public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


// 👇 핵심: 이 댓글의 주인(게시글)이 누구냐?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")// DB에는 board_id라는 이름으로 저장됨
    @JsonIgnore  // (중요) 이거 안 붙이면 무한루프 돌다가 서버 터짐. 일단 붙여.
    private Board board;

    // 👇 [추가] 댓글 좋아요 개수
    @Column
    private int likeCount = 0;

    // 생성자 수정
    public Comment(String content, Member member, Board board) {
        this.content = content;
        this.member = member;
        this.board = board;
    }


    public void increaseLike(){
        this.likeCount += 1;
    }
    public void decreaseLike(){
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }

    //댓글 만들때 쓸 생성자(ID 는 자동이니까 뺌)
    public Comment(String content, String writer, Board board){
        this.content = content;
        this.member = member;
        this.board = board;
    }
}

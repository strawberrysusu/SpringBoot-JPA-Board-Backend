package com.example.practicebackend1.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private int likeCount = 0;
    //생성자도 Member를 받도록 수정
    public Board(Long id, String title, String content, Member member, int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.likeCount = likeCount;
    }
    public void increaseLike(){
        this.likeCount += 1;
    }
    public void decreaseLike(){
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}

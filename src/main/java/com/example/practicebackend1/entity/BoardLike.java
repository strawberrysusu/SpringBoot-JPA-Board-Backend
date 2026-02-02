package com.example.practicebackend1.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
//// 👇 (꿀팁) 한 사람이 한 글에 좋아요 두 번 못 누르게 DB 차원에서 막기 (유니크 제약조건)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"board_id", "member_id"})
})
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //생성자
    public BoardLike(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

}

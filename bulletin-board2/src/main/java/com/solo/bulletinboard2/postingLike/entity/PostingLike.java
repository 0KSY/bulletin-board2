package com.solo.bulletinboard2.postingLike.entity;

import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.posting.entity.Posting;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostingLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingLikeId;

    @Column(nullable = false)
    private boolean liked = true;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "POSTING_ID")
    private Posting posting;
}

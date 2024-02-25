package com.solo.bulletinboard2.posting.entity;

import com.solo.bulletinboard2.comment.entity.Comment;
import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.postingLike.entity.PostingLike;
import com.solo.bulletinboard2.postingTag.entity.PostingTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<PostingTag> postingTags;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
    private List<PostingLike> postingLikes;

}

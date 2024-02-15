package com.solo.bulletinboard2.postingTag.entity;

import com.solo.bulletinboard2.posting.entity.Posting;
import com.solo.bulletinboard2.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostingTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postingTagId;

    @ManyToOne
    @JoinColumn(name = "POSTING_ID")
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
}

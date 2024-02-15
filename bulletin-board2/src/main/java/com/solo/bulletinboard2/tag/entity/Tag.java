package com.solo.bulletinboard2.tag.entity;

import com.solo.bulletinboard2.postingTag.entity.PostingTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false, unique = true, updatable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PostingTag> postingTags;

}

package com.solo.bulletinboard2.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @Setter
    public static class Post{
        @NotNull
        private String content;
        @Positive
        private long memberId;
        @Positive
        private long postingId;

        private long parentId;
    }

    @Getter
    @Setter
    public static class Patch{
        private long commentId;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long commentId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private long postingId;
        private MemberInfo memberInfo;
    }

    @Getter
    @Setter
    @Builder
    public static class MemberInfo{
        private long memberId;
        private String email;
        private String nickname;
    }
}

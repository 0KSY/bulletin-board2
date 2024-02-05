package com.solo.bulletinboard2.posting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class PostingDto {

    @Getter
    @Setter
    public static class Post{
        @NotNull
        private String title;
        @NotNull
        private String content;
        @Positive
        private long memberId;
    }

    @Getter
    @Setter
    public static class Patch{
        private long postingId;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long postingId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
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
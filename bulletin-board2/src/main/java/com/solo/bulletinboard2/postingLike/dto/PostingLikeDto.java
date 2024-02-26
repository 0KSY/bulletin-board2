package com.solo.bulletinboard2.postingLike.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PostingLikeDto {

    @Getter
    @Setter
    public static class Post{
        @Positive
        private long memberId;
        @Positive
        private long postingId;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long postingId;
        private int likeCount;
    }
}

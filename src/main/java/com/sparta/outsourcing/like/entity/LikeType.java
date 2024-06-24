package com.sparta.outsourcing.like.entity;

public enum LikeType {
    COMMENT(ContentType.COMMENT),
    BOARD(ContentType.BOARD);

    private final String contentType;
    LikeType(String contentType) {this.contentType = contentType; }

    public static class ContentType{
        public static final String COMMENT = "COMMENT";
        public static final String BOARD = "BOARD";
    }
}

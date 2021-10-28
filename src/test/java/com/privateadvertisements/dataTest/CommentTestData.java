package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.Comment;

import java.time.LocalDateTime;

public class CommentTestData {

    public static final Comment COMMENT_1 = new Comment(100008,
            UserTestData.USER_1,
            "Хороший товар",
            LocalDateTime.of(2021,10,12,18,46,23));

    public static final Comment COMMENT_2 = new Comment(100009,
            UserTestData.USER_2,
            "Супер",
            LocalDateTime.of(2021,10,12,18,46,23));

}

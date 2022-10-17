package com.tencent.wxcloudrun.model;

import lombok.Getter;

@Getter
public enum SortType {
    SORT_BY_LIKE("likeCount","DESC"),
    SORT_BY_TIME("commentTime","DESC"),
    SORT_BY_COURSE_NUM("courseNum","ASC"),
    SORT_BY_COMMENT_COUNT("commentCount","DESC"),
    SORT_BY_AVG_DIFFICULTY_DESC("avgDifficulty","DESC"),
    SORT_BY_AVG_DIFFICULTY_ASC("avgDifficulty","ASC"),
    SORT_BY_AVG_PREFER_DESC("avgPrefer","DESC"),
    SORT_BY_AVG_PREFER_ASC("avgPrefer","ASC"),
    ;

    private final String field;
    private final String order;

    SortType(String field, String order){
        this.field = field;
        this.order = order;
    }
}

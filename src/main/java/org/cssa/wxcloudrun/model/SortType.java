package org.cssa.wxcloudrun.model;

import lombok.Getter;

@Getter
public enum SortType {
    SORT_BY_LIKE("likeCount", "DESC", 0),
    SORT_BY_TIME("commentTime", "DESC", 0),
    SORT_BY_COURSE_NUM("courseNum", "ASC", 0),
    SORT_BY_COMMENT_COUNT("commentCount", "DESC", 1),
    SORT_BY_AVG_DIFFICULTY_DESC("avgDifficulty", "DESC", 1),
    SORT_BY_AVG_DIFFICULTY_ASC("avgDifficulty", "ASC", 1),
    SORT_BY_AVG_PREFER_DESC("avgPrefer", "DESC", 1),
    SORT_BY_AVG_PREFER_ASC("avgPrefer", "ASC", 1),
    ;

    private final String field;
    private final String order;
    private final Integer commentCount;

    SortType(String field, String order, Integer commentCount) {
        this.field = field;
        this.order = order;
        this.commentCount = commentCount;
    }
}

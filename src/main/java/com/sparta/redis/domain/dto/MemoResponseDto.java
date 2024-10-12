package com.sparta.redis.domain.dto;

import com.sparta.redis.domain.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;
    private String name;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) {
        id = memo.getId();
        name = memo.getName();
        title = memo.getTitle();
        contents = memo.getContents();
    }
}

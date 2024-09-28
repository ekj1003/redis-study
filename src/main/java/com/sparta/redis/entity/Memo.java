package com.sparta.redis.entity;

import com.sparta.redis.dto.MemoRequsetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    private String name;

    private String title;

    private String contents;

    public Memo(MemoRequsetDto memoRequsetDto) {
        name = memoRequsetDto.getName();
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
    }

    public Memo patchMemo(MemoRequsetDto memoRequsetDto) {
        name = memoRequsetDto.getName();
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
        return this;
    }
}

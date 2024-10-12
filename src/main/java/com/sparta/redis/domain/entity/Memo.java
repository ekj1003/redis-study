package com.sparta.redis.domain.entity;

import com.sparta.redis.domain.dto.MemoRequsetDto;
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

    public Memo(Long memoId, String name, String title, String contents) {
        this.id = memoId;
        this.name = name;
        this.title = title;
        this.contents = contents;
    }

    public Memo patchMemo(MemoRequsetDto memoRequsetDto) {
        name = memoRequsetDto.getName();
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
        return this;
    }
}

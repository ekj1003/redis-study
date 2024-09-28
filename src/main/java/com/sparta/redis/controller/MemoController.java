package com.sparta.redis.controller;

import com.sparta.redis.dto.MemoRequsetDto;
import com.sparta.redis.dto.MemoResponseDto;
import com.sparta.redis.repository.MemoRepository;
import com.sparta.redis.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequsetDto memoRequsetDto) {
        MemoResponseDto memoResponseDto = memoService.createMemo(memoRequsetDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> getMemo() {
        List<MemoResponseDto> memoResponseDtoList = memoService.getMemo();
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDtoList);
    }

    @PatchMapping("/{memoId}")
    public ResponseEntity<MemoResponseDto> patchMemo(@PathVariable("memoId") Long memoId, @RequestBody MemoRequsetDto memoRequsetDto) {
        MemoResponseDto memoResponseDto = memoService.patchMemo(memoId, memoRequsetDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    @DeleteMapping("/{memoId}")
    public void deleteMemo(@PathVariable("memoId") Long memoId) {
        memoService.deleteMemo(memoId);
    }

}

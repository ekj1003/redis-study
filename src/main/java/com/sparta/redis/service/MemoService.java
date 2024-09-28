package com.sparta.redis.service;


import com.sparta.redis.dto.MemoRequsetDto;
import com.sparta.redis.dto.MemoResponseDto;
import com.sparta.redis.entity.Memo;
import com.sparta.redis.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoResponseDto createMemo(MemoRequsetDto memoRequsetDto) {
        Memo memo = new Memo(memoRequsetDto);
        return new MemoResponseDto(memoRepository.save(memo));
    }

    public List<MemoResponseDto> getMemo() {
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public MemoResponseDto patchMemo(Long memoId, MemoRequsetDto memoRequsetDto) {
        Memo findMemo = memoRepository.findById(memoId).orElseThrow();
        return new MemoResponseDto(findMemo.patchMemo(memoRequsetDto));
    }

    public void deleteMemo(Long memoId) {
        memoRepository.delete(memoRepository.findById(memoId).orElseThrow());
    }
}

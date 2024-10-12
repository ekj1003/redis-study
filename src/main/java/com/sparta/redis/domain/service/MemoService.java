package com.sparta.redis.domain.service;

import com.sparta.redis.config.redis.RedisUtils;
import com.sparta.redis.domain.dto.MemoRequsetDto;
import com.sparta.redis.domain.dto.MemoResponseDto;
import com.sparta.redis.domain.entity.Memo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final RedisUtils redisUtils;  // RedisUtils 주입

    // Redis에서 고유한 Long 타입의 ID를 생성하는 메서드
    private Long generateMemoId() {
        Object memoIdObj = redisUtils.getData("memo_id_seq");

        Long memoId;
        if (memoIdObj instanceof Integer) {
            memoId = ((Integer) memoIdObj).longValue();  // Integer를 Long으로 변환
        } else if (memoIdObj instanceof Long) {
            memoId = (Long) memoIdObj;
        } else {
            memoId = 1L;  // 처음 저장할 경우
        }

        memoId++;  // ID 증가
        redisUtils.setData("memo_id_seq", memoId, 300000L);  // ID 시퀀스를 Redis에 저장 (5분 만료)
        return memoId;
    }


    // 메모 생성 및 Redis에 저장
    public MemoResponseDto createMemo(MemoRequsetDto memoRequsetDto) {
        // 고유한 Long 타입 ID 생성
        Long memoId = generateMemoId();

        // Memo 객체 생성
        Memo memo = new Memo(memoId, memoRequsetDto.getName(), memoRequsetDto.getTitle(), memoRequsetDto.getContents());

        // Redis에 Memo 객체 저장
        redisUtils.setData("memo:" + memoId, memo, 300000L);  // 만료 시간 5분

        return new MemoResponseDto(memo);
    }

    // 모든 메모 조회 (Redis에서 SCAN 명령어 사용)
    public List<MemoResponseDto> getMemo() {
        List<MemoResponseDto> memoList = new ArrayList<>();

        // RedisConnection을 통해 SCAN 명령어 실행
        RedisConnection redisConnection = redisUtils.getRedisTemplate().getConnectionFactory().getConnection();
        ScanOptions scanOptions = ScanOptions.scanOptions().match("memo:*").count(100).build();

        Cursor<byte[]> cursor = redisConnection.scan(scanOptions);

        while (cursor.hasNext()) {
            // 각 키를 UTF-8로 변환하여 문자열로 처리
            String key = new String(cursor.next(), StandardCharsets.UTF_8);

            // Redis에서 키에 해당하는 메모를 가져옴
            Memo memo = (Memo) redisUtils.getData(key);
            if (memo != null) {
                memoList.add(new MemoResponseDto(memo));
            }
        }

        try {
            cursor.close();  // 커서 닫기
        } catch (Exception e) {
            e.printStackTrace();
        }

        return memoList;
    }

    // 메모 수정
    public MemoResponseDto patchMemo(Long memoId, MemoRequsetDto memoRequsetDto) {
        // Redis에서 기존 메모 조회
        Memo memo = (Memo) redisUtils.getData("memo:" + memoId);

        if (memo == null) {
            throw new IllegalArgumentException("Memo not found");
        }

        // 메모 수정
        memo.patchMemo(memoRequsetDto);

        // 수정된 Memo Redis에 다시 저장
        redisUtils.setData("memo:" + memoId, memo, 300000L);

        return new MemoResponseDto(memo);
    }

    // 메모 삭제
    public void deleteMemo(Long memoId) {
        // Redis에서 메모 삭제
        redisUtils.deleteData("memo:" + memoId);
    }
}

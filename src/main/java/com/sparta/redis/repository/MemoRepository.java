package com.sparta.redis.repository;

import com.sparta.redis.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}

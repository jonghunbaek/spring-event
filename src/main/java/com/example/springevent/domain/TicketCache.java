package com.example.springevent.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@ToString
@Getter
@NoArgsConstructor
@RedisHash
public class TicketCache {

    @Id
    private Long memberId;

    private int remainingTimes;

    public TicketCache(Long memberId, int remainingTimes) {
        this.memberId = memberId;
        this.remainingTimes = remainingTimes;
    }

    public void validateRemainingTimes() {
        if (remainingTimes <= 0) {
            throw new IllegalArgumentException("해당 이용권의 사용횟수는 모두 소진되었습니다.");
        }
    }
}

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
}

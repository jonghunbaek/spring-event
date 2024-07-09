package com.example.springevent.common.cache.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class TicketCache {

    private Long memberId;
    private int remainingTimes;

    public TicketCache(Long memberId, int remainingTimes) {
        this.memberId = memberId;
        this.remainingTimes = remainingTimes;
    }

    public void validateRemainingTimes() {
        if (remainingTimes <= 0) {
            throw new IllegalArgumentException("사용 가능한 이용권 잔여 횟수가 없습니다.");
        }
    }
}

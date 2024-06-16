package com.example.springevent.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TicketInfo {

    private String title;

    private int remainingTimes;

    private long memberId;

    public TicketInfo(String title, int remainingTimes, long memberId) {
        this.title = title;
        this.remainingTimes = remainingTimes;
        this.memberId = memberId;
    }
}

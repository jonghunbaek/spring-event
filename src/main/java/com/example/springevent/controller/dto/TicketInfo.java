package com.example.springevent.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TicketInfo {

    private String title;

    private int remainingTimes;

    public TicketInfo(String title, int remainingTimes) {
        this.title = title;
        this.remainingTimes = remainingTimes;
    }
}

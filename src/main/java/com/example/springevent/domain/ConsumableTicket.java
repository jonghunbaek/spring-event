package com.example.springevent.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class ConsumableTicket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int remainingTimes;

    public ConsumableTicket(String title, int remainingTimes) {
        this.title = title;
        this.remainingTimes = remainingTimes;
    }
}

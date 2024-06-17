package com.example.springevent.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class ConsumableTicket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int remainingTimes;

    @ManyToOne
    private Member member;

    public ConsumableTicket(String title, int remainingTimes, Member member) {
        this.title = title;
        this.remainingTimes = remainingTimes;
        this.member = member;
    }

    public void deductRemainigTimes() {
        this.remainingTimes--;
    }
}

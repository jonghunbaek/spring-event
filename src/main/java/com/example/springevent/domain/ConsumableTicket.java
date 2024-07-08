package com.example.springevent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
        validateRemainingTimes();
        this.remainingTimes--;
    }

    public void validateRemainingTimes() {
        if (remainingTimes <= 0) {
            throw new IllegalArgumentException("사용 가능한 이용권 잔여 횟수가 없습니다.");
        }
    }
}

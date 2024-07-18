package com.example.springevent.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class TicketEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishTicketDeductionEvent(Long memberId) {
        eventPublisher.publishEvent(memberId);
    }
}

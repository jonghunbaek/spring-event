package com.example.springevent.service;

import com.example.springevent.common.event.TicketEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainService {

    private final TicketEventPublisher ticketEventPublisher;

    @Transactional
    public String getMessage(long memberId, String message) {
        ticketEventPublisher.publishTicketDeductionEvent(memberId);

        if ("ex".equals(message)) {
            throw new IllegalArgumentException("비즈니스 로직 예외 발생!");
        }

        // 비즈니스 로직을 수행했다고 가정
        log.info("message :: {}", message);

        return message;
    }
}

package com.example.springevent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainService {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void getMessage(long memberId, String message) {
        eventPublisher.publishEvent(memberId);
        
        // 비즈니스 로직을 수행했다고 가정
        log.info("message :: {}", message);
        
        if ("ex".equals(message)) {
            throw new IllegalStateException("예외 발생");
        }
    }
}

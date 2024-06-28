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
        // 이용권 검증 및 횟수 차감 후 비즈니스 로직 수행
        log.info("message :: {}", message);
    }
}

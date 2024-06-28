package com.example.springevent.common.event;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class TicketEventListener {

    private final TicketCacheManager ticketCacheManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(value = MainService.class, phase = TransactionPhase.BEFORE_COMMIT) // 이벤트가 발행된 곳의 트랜잭션 커밋 전에 실행
    public void ticketCountDeductor(long memberId) {
        ConsumableTicket consumableTicket = ticketCacheManager.getTicket(memberId);

        consumableTicket.deductRemainigTimes();

        ticketCacheManager.updateTicketCache(consumableTicket);
    }
}

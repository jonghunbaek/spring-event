package com.example.springevent.event;

import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.domain.TicketCache;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class TicketEventListener {

    private final ConsumableTicketRepository ticketRepository;
    private final CacheManager cacheManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // 이벤트 발행된 곳의 트랜잭션 커밋 후에만 실행
    public void ticketCountDeductor(long memberId) {
        ConsumableTicket consumableTicket = ticketRepository.findByMemberId(memberId)
                .orElseThrow();

        consumableTicket.deductRemainigTimes();
        Cache cache = cacheManager.getCache("ticket");
        cache.put(1L, new TicketCache(memberId, consumableTicket.getRemainingTimes()));
    }
}

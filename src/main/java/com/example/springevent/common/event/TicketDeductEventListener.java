package com.example.springevent.common.event;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
public class TicketDeductEventListener {

    private final TicketCacheManager ticketCacheManager;
    private final ConsumableTicketRepository ticketRepository;

    @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 5, backoff = @Backoff(delay = 3000, maxDelay = 5000))
    @Transactional(propagation = Propagation.REQUIRES_NEW) // REQUIRES_NEW 아니면 예외 발생
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // 이벤트가 발행된 곳의 트랜잭션 커밋 후에 실행
    public void deductTicketCount(Long memberId) {
        ConsumableTicket consumableTicket = ticketRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하는 이용권이 없습니다."));

        if (memberId.intValue() == 2) {
            throw new IllegalArgumentException("이벤트 리스너 예외 발생!");
        }

        consumableTicket.deductRemainigTimes();

        ticketCacheManager.updateTicketCache(consumableTicket);
    }
}

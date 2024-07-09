package com.example.springevent.common.cache;

import com.example.springevent.common.cache.dto.TicketCache;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TicketCacheManager {

    private final ConsumableTicketRepository ticketRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "#memberId", value = "ticket")
    public TicketCache getTicket(Long memberId) {
        ConsumableTicket consumableTicket = ticketRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하는 이용권이 없습니다."));

        return new TicketCache(memberId, consumableTicket.getRemainingTimes());
    }

    @CachePut(key = "#consumableTicket.member.id", value = "ticket")
    public TicketCache updateTicketCache(ConsumableTicket consumableTicket) {
        return new TicketCache(consumableTicket.getMember().getId(), consumableTicket.getRemainingTimes());
    }
}

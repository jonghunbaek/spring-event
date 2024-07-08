package com.example.springevent.common.cache;

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

    // TODO :: 캐싱되는 값 엔티티 -> DTO로 변경해야함
    @Transactional(readOnly = true)
    @Cacheable(key = "#memberId", value = "ticket")
    public ConsumableTicket getTicket(Long memberId) {
        return ticketRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalStateException("존재하는 이용권이 없습니다."));
    }

    @CachePut(key = "#consumableTicket.member.id", value = "ticket")
    public ConsumableTicket updateTicketCache(ConsumableTicket consumableTicket) {
        return consumableTicket;
    }
}

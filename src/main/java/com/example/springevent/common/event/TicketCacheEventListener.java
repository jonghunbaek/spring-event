package com.example.springevent.common.event;

import com.example.springevent.common.filter.TicketAuthFilter;
import com.example.springevent.domain.TicketCache;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TicketCacheEventListener {

    private final ConsumableTicketRepository ticketRepository;
    private final CacheManager cacheManager;

    @EventListener(TicketAuthFilter.class)
    public void putTicketToCache(Long memberId) {
        Cache cache = cacheManager.getCache("ticket");

        ticketRepository.findByMemberId(memberId)
            .ifPresentOrElse(
                ticket -> cache.put(memberId, new TicketCache(memberId, ticket.getRemainingTimes())),
                () -> cache.put(memberId, new TicketCache(memberId, 0))
            );
    }
}

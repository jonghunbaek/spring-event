package com.example.springevent.common.filter;

import com.example.springevent.domain.TicketCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TicketAuthFilter extends OncePerRequestFilter {

    private final ApplicationEventPublisher eventPublisher;
    private final CacheManager cacheManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cache cache = cacheManager.getCache("ticket");

        // jwt에서 memberId 꺼내는 로직 추가
        // memberId로 캐시 조회 - 현재는 임시로
        Optional<TicketCache> ticketCacheOpt = Optional.ofNullable(cache.get(1L, TicketCache.class));

        if (ticketCacheOpt.isEmpty()) {
            eventPublisher.publishEvent(1L);
        }

        ticketCacheOpt.get().validateRemainingTimes();

        filterChain.doFilter(request, response);
    }
}

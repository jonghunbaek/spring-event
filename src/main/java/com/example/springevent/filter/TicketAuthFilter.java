package com.example.springevent.filter;

import com.example.springevent.domain.TicketCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TicketAuthFilter extends OncePerRequestFilter {

    private final CacheManager cacheManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cache cache = cacheManager.getCache("ticket");

        // memberId로 캐시 조회 - 현재는 임시로
        Optional<TicketCache> ticketCache = Optional.ofNullable(cache.get(1L, TicketCache.class));

        if (ticketCache.isEmpty()) {
            throw new IllegalArgumentException("존재하는 이용권이 없습니다.");
        }

        ticketCache.get().validateRemainingTimes();

        filterChain.doFilter(request, response);
    }
}

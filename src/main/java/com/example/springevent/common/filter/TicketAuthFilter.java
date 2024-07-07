package com.example.springevent.common.filter;

import com.example.springevent.domain.TicketCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
//@Component
public class TicketAuthFilter extends OncePerRequestFilter {

    private final CacheManager cacheManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return requestURI.contains("sign-in") || requestURI.contains("sign-up");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cache cache = cacheManager.getCache("ticket");

        // access token 디코딩해서 사용자 id 추출
        long memberId = getMemberId(request);

        // memberId로 저장된 캐시 조회
        TicketCache ticketCache;
        try {
            ticketCache = cache.get(memberId, TicketCache.class);
            ticketCache.validateRemainingTimes();
        } catch (NullPointerException e) {
            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private long getMemberId(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        String accessToken = bearerToken.substring(7);

        return Long.parseLong(decodeJwtPayload(accessToken));
    }

    private String decodeJwtPayload(String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new String(Base64.getDecoder().decode(accessToken.split("\\.")[1]), StandardCharsets.UTF_8), Map.class)
                .get("sub")
                .toString();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}

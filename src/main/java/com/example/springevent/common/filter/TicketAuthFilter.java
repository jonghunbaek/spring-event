package com.example.springevent.common.filter;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.domain.ConsumableTicket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.springframework.http.HttpHeaders.*;

@RequiredArgsConstructor
@Component
public class TicketAuthFilter extends OncePerRequestFilter {

    private final TicketCacheManager ticketCacheManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return requestURI.contains("sign-in") || requestURI.contains("sign-up");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // access token 디코딩해서 사용자 id 추출
        long memberId = getMemberId(request);

        // memberId로 저장된 캐시 조회
        // 이용권이 전혀 존재 하지 않는 경우 - 1차 검증
        // 이용권의 잔여 이용 횟수가 0이하인 경우 - 2차 검증
        ConsumableTicket consumableTicket = ticketCacheManager.getTicket(memberId);
        consumableTicket.validateRemainingTimes();

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

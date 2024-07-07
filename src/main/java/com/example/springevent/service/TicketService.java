package com.example.springevent.service;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.domain.Member;
import com.example.springevent.repository.ConsumableTicketRepository;
import com.example.springevent.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketCacheManager ticketCacheManager;

    private final ConsumableTicketRepository ticketRepository;
    private final MemberRepository memberRepository;

    public void createTicket(String title, int remainingTimes, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        ConsumableTicket consumableTicket = new ConsumableTicket(title, remainingTimes, member);

        ticketRepository.save(consumableTicket);
    }

    public ConsumableTicket getTicket(Long memberId) {
        return ticketCacheManager.getTicket(memberId);
    }
}

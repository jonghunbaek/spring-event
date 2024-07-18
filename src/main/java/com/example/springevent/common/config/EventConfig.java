package com.example.springevent.common.config;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.common.event.TicketEventPublisher;
import com.example.springevent.common.event.TicketDeductEventListener;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EventConfig {

    @Bean
    public TicketEventPublisher ticketDeductEventPublisher(ApplicationEventPublisher eventPublisher) {
        return new TicketEventPublisher(eventPublisher);
    }

    @Bean
    public TicketDeductEventListener ticketEventListener(TicketCacheManager ticketCacheManager, ConsumableTicketRepository ticketRepository) {
        return new TicketDeductEventListener(ticketCacheManager, ticketRepository);
    }
}

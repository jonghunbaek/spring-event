package com.example.springevent.service;

import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final ConsumableTicketRepository ticketRepository;

    public void createTicket(String title, int remainingTimes) {
        ConsumableTicket consumableTicket = new ConsumableTicket(title, remainingTimes);

        ticketRepository.save(consumableTicket);
    }
}

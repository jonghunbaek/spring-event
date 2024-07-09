package com.example.springevent.controller;

import com.example.springevent.common.cache.dto.TicketCache;
import com.example.springevent.controller.dto.TicketInfo;
import com.example.springevent.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tickets")
@RequiredArgsConstructor
@RestController
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public TicketCache getTicket(@RequestParam String memberId) {
        return ticketService.getTicket(Long.parseLong(memberId));
    }

    @PostMapping
    public void createTicket(@RequestBody TicketInfo ticketInfo) {
        ticketService.createTicket(ticketInfo.getTitle(), ticketInfo.getRemainingTimes(), ticketInfo.getMemberId());
    }
}

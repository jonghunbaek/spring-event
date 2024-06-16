package com.example.springevent.controller;

import com.example.springevent.controller.dto.TicketInfo;
import com.example.springevent.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tickets")
@RequiredArgsConstructor
@RestController
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public void createTicket(@RequestBody TicketInfo ticketInfo) {
        ticketService.createTicket(ticketInfo.getTitle(), ticketInfo.getRemainingTimes(), ticketInfo.getMemberId());
    }
}

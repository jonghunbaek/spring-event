package com.example.springevent.repository;

import com.example.springevent.domain.ConsumableTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumableTicketRepository extends JpaRepository<ConsumableTicket, Long> {
}

package com.example.springevent.repository;

import com.example.springevent.domain.ConsumableTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumableTicketRepository extends JpaRepository<ConsumableTicket, Long> {

    Optional<ConsumableTicket> findByMemberId(long memberId);
}

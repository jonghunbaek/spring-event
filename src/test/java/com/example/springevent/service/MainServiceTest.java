package com.example.springevent.service;

import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@SpringBootTest
class MainServiceTest {

    @Autowired
    MainService mainService;

    @Autowired
    ConsumableTicketRepository ticketRepository;

    @DisplayName("메인 서비스의 메서드가 호출되면 이벤트를 발행해 이용권의 개수를 1개 차감한다.")
    @Test
    void publicEventWheMainServiceCall() {
        // given
        ConsumableTicket consumableTicket = ticketRepository.findByMemberId(1L)
                .orElseThrow();

        assertThat(consumableTicket.getRemainingTimes()).isEqualTo(10);

        // when
        mainService.getMessage(1L, "테스트 메세지");

        consumableTicket = ticketRepository.findByMemberId(1L)
                .orElseThrow();

        // then
        assertThat(consumableTicket.getRemainingTimes()).isEqualTo(9);
    }
}
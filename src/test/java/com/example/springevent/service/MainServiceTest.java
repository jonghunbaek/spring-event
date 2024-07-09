package com.example.springevent.service;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.common.cache.dto.TicketCache;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MainServiceTest {

    @Autowired
    MainService mainService;

    @Autowired
    TicketCacheManager ticketCacheManager;

    @Autowired
    ConsumableTicketRepository ticketRepository;

    /**
     * Q1. @Transactional이 붙은 테스트를 실행하면 왜 이벤트 리슨이 안될까?
     *      -> 테스트 트랜잭션은 무조건 롤백이기 때문에 테스트가 끝나거나 롤백된 이후에 이벤트가 처리됨
     */
    @DisplayName("메인 서비스의 메서드가 호출되면 이벤트를 발행해 이용권의 개수를 1개 차감한다.")
    @Test
    void publicEventWheMainServiceCall() {
        // given
        mainService.getMessage(1L, "테스트 메세지");

        // when
        TicketCache ticket = ticketCacheManager.getTicket(1L);

        // then
        assertThat(ticket.getRemainingTimes()).isEqualTo(9);
    }
}
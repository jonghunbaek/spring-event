package com.example.springevent.service;

import com.example.springevent.common.cache.TicketCacheManager;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MainServiceTest {

    @Autowired
    MainService mainService;

    @Autowired
    TicketCacheManager ticketCacheManager;

    @Autowired
    ConsumableTicketRepository ticketRepository;

//    @Transactional
    /**
     * Q1. @Transactional이 붙은 테스트를 실행하면 왜 이벤트 리슨이 안될까?
     * Q2. @Cachealbe, @CachePut을 활용해 데이터를 캐싱하는데 왜 두 번째까진 DB에서 조회할까?
     *      -> 예를 들어, 10번 getTicket()을 호출했다하면 최초 1회 이후 1번더 데이터를 캐싱한다
     *      -> 캐시 만료시간을 확인해볼 것
     */
    @DisplayName("메인 서비스의 메서드가 호출되면 이벤트를 발행해 이용권의 개수를 1개 차감한다.")
    @Test
    void publicEventWheMainServiceCall() {
        // given
        mainService.getMessage(1L, "테스트 메세지");

        // when
        ConsumableTicket consumableTicket = ticketCacheManager.getTicket(1L);

        // then
        assertThat(consumableTicket.getRemainingTimes()).isEqualTo(9);
    }

    @DisplayName("이용권 캐시 테스트")
    @Test
    void cacheTest() {
        for (int i=0; i<10; i++) {
            ConsumableTicket consumableTicket = ticketCacheManager.getTicket(1L);
        }
    }
}
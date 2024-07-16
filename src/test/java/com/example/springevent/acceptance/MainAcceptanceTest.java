package com.example.springevent.acceptance;

import com.example.springevent.common.jwt.JwtManager;
import com.example.springevent.domain.ConsumableTicket;
import com.example.springevent.repository.ConsumableTicketRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    JwtManager jwtManager;

    @Autowired
    ConsumableTicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("메인 API 호출 통합 테스트(이벤트 발행, 구독 확인) - 정상 호출인 경우")
    @Test
    void mainAcceptanceTest() {
        // given
        String accessToken = jwtManager.createAccessToken(1L);

        ExtractableResponse<Response> result = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("message", "test message")
                // when
                .when()
                .get("/main/1/sample")
                // then
                .then()
                .log().all()
                .extract();

        ConsumableTicket ticket = ticketRepository.findByMemberId(1L)
                .orElseThrow();

        assertAll(
                () -> assertEquals(200, result.statusCode()),
                () -> assertEquals(9, ticket.getRemainingTimes())
        );
    }

    @DisplayName("메인 API 호출 통합 테스트(이벤트 발행, 구독 확인) - 비즈니스 로직에서 예외가 발생한 경우")
    @Test
    void mainAcceptanceTestWhenException() {
        // given
        String accessToken = jwtManager.createAccessToken(1L);

        // when
        ExtractableResponse<Response> result = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("message", "ex")
                .when()
                .get("/main/1/sample")
                .then()
                .log().all()
                .extract();

        ConsumableTicket ticket = ticketRepository.findByMemberId(1L)
                .orElseThrow();

        // then
        assertAll(
                () -> assertEquals(403, result.statusCode()),
                () -> assertEquals(10, ticket.getRemainingTimes())
        );
    }
}

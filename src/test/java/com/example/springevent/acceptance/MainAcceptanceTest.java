package com.example.springevent.acceptance;

import com.example.springevent.common.jwt.JwtManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest()
public class MainAcceptanceTest {

    @Autowired
    JwtManager jwtManager;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @DisplayName("메인 API 호출 통합 테스트(이벤트 발행, 구독 확인)")
    @Test
    void mainAcceptanceTest() {
        // given
        String accessToken = jwtManager.createAccessToken(1L);

        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("message", "test message")
        // when
                .when()
                .get("/1/sample")
        // then
                .then()
                .log().all()
                .extract();
    }
}

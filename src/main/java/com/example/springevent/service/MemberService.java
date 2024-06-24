package com.example.springevent.service;

import com.example.springevent.common.jwt.JwtManager;
import com.example.springevent.common.jwt.Tokens;
import com.example.springevent.domain.Member;
import com.example.springevent.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    public void signUp(String email, String password) {
        memberRepository.save(new Member(email, password));
    }

    public Tokens signIn(String email, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow();

        member.validatePassword(password);

        String accessToken = jwtManager.createAccessToken(member.getId());
        String refreshToken = jwtManager.createRefreshToken();

        return new Tokens(accessToken, refreshToken);
    }
}

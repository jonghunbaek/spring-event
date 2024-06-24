package com.example.springevent.controller;

import com.example.springevent.common.jwt.Tokens;
import com.example.springevent.controller.dto.SignInInfo;
import com.example.springevent.controller.dto.SignUpInfo;
import com.example.springevent.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpInfo signUpInfo) {
        memberService.signUp(signUpInfo.getEmail(), signUpInfo.getPassword());
    }

    @PostMapping("/sign-in")
    public Tokens signIn(@RequestBody SignInInfo signInInfo) {
        return memberService.signIn(signInInfo.getEmail(), signInInfo.getPassword());
    }
}

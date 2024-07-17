package com.example.springevent.controller;

import com.example.springevent.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/main")
@RequiredArgsConstructor
@RestController
public class MainController {

    private final MainService mainService;

    @GetMapping("{memberId}/sample")
    public String getSampleApi(@PathVariable long memberId, @RequestParam String message) {
        return mainService.getMessage(memberId, message);
    }
}

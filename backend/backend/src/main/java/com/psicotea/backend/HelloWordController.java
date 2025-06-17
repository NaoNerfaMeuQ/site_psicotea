package com.psicotea.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Indica que é um controller Rest
@RequestMapping("/api")

public class HelloWordController {
    @GetMapping("/hello")
    public String hello() {
        return "Olá do backend Java!";
    }
}

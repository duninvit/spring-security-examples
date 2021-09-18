package com.github.duninvit.keycloakresourseserver.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('sampleadmin')")
    public String hello() {
        return "Hello!";
    }
}

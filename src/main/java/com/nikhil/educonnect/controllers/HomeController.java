package com.nikhil.educonnect.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

        @GetMapping("/")
        public String home() {
            return "Server is running 🚀";
        }

        @GetMapping("/health")
        public String healthCheck() {
            return "Application is healthy ✅";
        }
}

package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final Environment environment;

    @Autowired
    public InfoController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/port")
    public String getPort() {
        return "Приложение запущено на порту: " + environment.getProperty("local.server.port");
    }

}

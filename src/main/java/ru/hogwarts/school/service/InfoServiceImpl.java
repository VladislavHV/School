package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    private final Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Autowired
    public InfoServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getPort() {
        logger.info("Вызван метод getPort");
        return "Приложение запущено на порту: " + environment.getProperty("local.server.port");
    }

    @Override
    public long slowSum() {
        logger.info("Вызван метод slowSum");
        return Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
    }

    @Override
    public long fastSum() {
        logger.info("Вызван метод fastSum");
        long n = 1_000_000;
        return n * (n + 1) / 2;
    }

}

package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InfoServiceTest {

    @Mock
    private Environment environment;

    private InfoServiceImpl infoService;

    @BeforeEach
    void setUp() {
        environment = mock(Environment.class);
        infoService = new InfoServiceImpl(environment);
    }

    @Test
    void testGetPort_ReturnMessage() {
        when(environment.getProperty("local.server.port")).thenReturn("8080");
        String result = infoService.getPort();
        assertEquals("Приложение запущено на порту: 8080", result);
    }

    @Test
    void testSlowSum_ReturnResult() {
        long result = infoService.slowSum();
        assertEquals(500000500000L, result);
    }

    @Test
    void testFastSum_ReturnResult() {
        long result = infoService.fastSum();
        assertEquals(500000500000L, result);
    }

}

package com.finaro;

import com.finaro.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationITest extends AbstractIntegrationTest {
    @Autowired
    protected ConfigurableWebApplicationContext context;

    @Test
    void shouldLoadApplicationContext() {
        assertTrue(context.isRunning());
    }
}
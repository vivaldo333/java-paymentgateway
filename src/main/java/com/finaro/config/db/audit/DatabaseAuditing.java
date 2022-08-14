package com.finaro.config.db.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DatabaseAuditing implements AuditorAware<String> {
    private static final String DEFAULT_USER = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO (2022-08-15): add security principal
        return Optional.of(DEFAULT_USER);
    }
}

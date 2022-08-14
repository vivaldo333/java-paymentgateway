package com.finaro;

import com.finaro.config.audit.AuditProperties;
import com.finaro.logging.dto.LogConfidentialData;
import com.finaro.logging.dto.MaskObjectExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.finaro.constant.Constants.DateTime.DEFAULT_TIME_ZONE;
import static com.finaro.constant.Constants.LogMarkers.CONFIDENTIAL;

@Slf4j
@EnableAspectJAutoProxy
@EnableJpaAuditing(modifyOnCreate = false, auditorAwareRef = "databaseAuditing")
@EnableConfigurationProperties(AuditProperties.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void init() {
        logConfidentialData();
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
    }

    /**
     * @deprecated (2022.08.14, an example of logging confidential info, for removal)
     */
    @Deprecated(forRemoval = true)
    private static void logConfidentialData() {
        var dataWithConfidentialInfo = new MaskObjectExample();
        dataWithConfidentialInfo.setUserName("user");
        dataWithConfidentialInfo.setPassword(new LogConfidentialData<>("pwd"));
        log.info(CONFIDENTIAL, "info with inherit confidential: [{}], confidential: [{}], public: [{}]",
                dataWithConfidentialInfo, new LogConfidentialData<>("cvv"), "public info");
    }
}

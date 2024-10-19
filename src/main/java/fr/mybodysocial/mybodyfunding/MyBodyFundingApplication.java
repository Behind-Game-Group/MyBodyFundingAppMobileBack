package fr.mybodysocial.mybodyfunding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MyBodyFundingApplication {
    private static final Logger LOGGER = LogManager.getLogger(MyBodyFundingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyBodyFundingApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

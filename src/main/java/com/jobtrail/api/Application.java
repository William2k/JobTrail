package com.jobtrail.api;

import com.jobtrail.api.config.ConfigProperties;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.User;
import com.jobtrail.api.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final AccountService accountService;
    private final ConfigProperties configProperties;

    public Application(AccountService accountService, ConfigProperties configProperties) {
        this.accountService = accountService;
        this.configProperties = configProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... params) {
        initialise();
    }

    private void initialise() {
        ConversionHelper.objectMapperInit();
    }
}

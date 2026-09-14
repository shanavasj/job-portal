package com.shanavas.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JobPortalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);
    }

    // Needed because we're packaging as a WAR (JSP requires WAR, not JAR)
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JobPortalApplication.class);
    }
}

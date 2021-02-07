package edu.ahau.graduationproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan("edu.ahau.graduationproject.mapper")
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
public class GraduationprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationprojectApplication.class, args);
    }

}

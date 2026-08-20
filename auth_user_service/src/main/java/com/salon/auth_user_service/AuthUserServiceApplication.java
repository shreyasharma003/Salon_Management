package com.salon.auth_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthUserServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(AuthUserServiceApplication.class, args);
        System.out.println("<-----------Auth_User_service Application Started------------>");
	}

}

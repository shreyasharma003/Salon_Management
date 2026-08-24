package com.salon.attendanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AttendanceserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceserviceApplication.class, args);
	}

}

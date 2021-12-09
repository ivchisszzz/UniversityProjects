package com.project.masters.hotelManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@EnableJpaRepositories
@SpringBootApplication
public class HotelManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotelManagementApplication.class, args);
  }

}

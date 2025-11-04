package br.edu.infnet.jessefigueroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JessefigueroapiApplication {

	public static void main(String[] args) {
        SpringApplication.run(JessefigueroapiApplication.class, args);
	}

}

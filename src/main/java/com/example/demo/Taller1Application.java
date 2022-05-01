package com.example.demo;

import com.example.demo.model.UserType;
import com.example.demo.model.Userr;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.VendorService;
import com.example.demo.services.VendorServiceImp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/* @EnableJpaRepositories(basePackages = { "com.example.demo.repositories" })
@EntityScan(basePackages = { "com.example.demo.model" })*/
@ComponentScan(basePackages = { "com.example.demo" })
@SpringBootApplication
public class Taller1Application {

	public static void main(String[] args) {
		SpringApplication.run(Taller1Application.class, args);
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, VendorServiceImp vendorService,
			BusinessEntityRepository ber) {
		return (args) -> {

			Vendor v1 = new Vendor();
			v1.setName("Sebastian");
			v1.setCreditrating(12);
			v1.setPurchasingwebserviceurl("https:/amazon.com");
			vendorService.save(v1);

			Userr u1 = new Userr();
			u1.setUsername("admin");
			u1.setId(1);
			u1.setPassword("{noop}admin");
			u1.setUserType(UserType.ADMINISTRADOR);
			userRepository.save(u1);

			Userr u2 = new Userr();
			u2.setUsername("operador");
			u2.setId(2);
			u2.setPassword("{noop}operador");
			u2.setUserType(UserType.OPERADOR);
			userRepository.save(u2);

		};
	}

}

package com.example.demo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.example.demo.model.UserType;
import com.example.demo.model.Userr;
import com.example.demo.model.hr.Employee;
import com.example.demo.model.person.Person;
import com.example.demo.model.prchasing.Purchaseorderdetail;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.model.prchasing.Shipmethod;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.PurchaseOrderDetailRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.repositories.ShipMethodRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PurchaseOrderDetailService;
import com.example.demo.services.PurchaseOrderHeaderService;
import com.example.demo.services.ShipMethodService;
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
			BusinessEntityRepository ber, ShipMethodService sms, ShipMethodRepository smr,
			PurchaseOrderDetailRepository podr, PurchaseOrderDetailService pods, PurchaseOrderHeaderService pohs,
			PurchaseOrderHeaderRepository pohr, PersonRepository personRepository,
			EmployeeRepository employeeRepository) {
		return (args) -> {
			Person p1 = new Person();
			p1.setFirstname("Alfonso");
			p1.setLastname("Bustamante");
			personRepository.save(p1);

			Person p2 = new Person();
			p2.setFirstname("Luis");
			p2.setLastname("Arias");
			personRepository.save(p2);

			Person p3 = new Person();
			p3.setFirstname("Luisa");
			p3.setLastname("Ramirez");
			personRepository.save(p3);

			Employee e1 = new Employee();
			e1.setGender("masculino");
			employeeRepository.save(e1);

			Employee e2 = new Employee();
			e2.setGender("masculino");
			employeeRepository.save(e2);

			Employee e3 = new Employee();
			e3.setGender("femenino");
			employeeRepository.save(e3);

			Purchaseorderheader poh1 = new Purchaseorderheader();
			poh1.setOrderdate(Timestamp.valueOf(LocalDateTime.now()));
			poh1.setSubtotal(new BigDecimal(15));
			pohs.save(poh1, p1.getBusinessentityid(), e1.getBusinessentityid());

			Purchaseorderdetail pod1 = new Purchaseorderdetail();
			pod1.setOrderqty(10);
			pod1.setUnitprice(new BigDecimal(3));
			pods.save(pod1, poh1.getPurchaseorderid());
			System.out.println("HOLA:  " + pod1.getPurchaseorderheader().getPurchaseorderid());

			Shipmethod sm1 = new Shipmethod();
			sm1.setName("Ship method 1");
			sm1.setShipbase(new BigDecimal(15));
			sm1.setShiprate(new BigDecimal(10));
			sms.save(sm1);

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

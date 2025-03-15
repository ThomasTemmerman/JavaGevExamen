package com.example.javaAdvancedExamen;

import com.example.javaAdvancedExamen.dto.CampusDTO;
import com.example.javaAdvancedExamen.entity.Campus;
import com.example.javaAdvancedExamen.repository.CampusRepository;
import com.example.javaAdvancedExamen.service.CampusService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//ConfigurableApplicationContext context =
		SpringApplication.run(Application.class, args);

		//CampusRepository repository =context.getBean(CampusRepository.class);
		//repository.save(new Campus("Test","Testadres",5));

	}

}

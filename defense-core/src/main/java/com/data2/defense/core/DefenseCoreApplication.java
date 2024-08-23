package com.data2.defense.core;

import com.data2.defense.core.component.PortScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DefenseCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefenseCoreApplication.class, args).getBean(PortScanService.class).exists();
	}

}

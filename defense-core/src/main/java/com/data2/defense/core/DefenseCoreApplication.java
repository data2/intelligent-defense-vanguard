package com.data2.defense.core;

import com.data2.defense.core.component.PortScanService;
import com.data2.defense.core.run.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DefenseCoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DefenseCoreApplication.class, args);
		ctx.getBean(TestService.class).test();
	}

}

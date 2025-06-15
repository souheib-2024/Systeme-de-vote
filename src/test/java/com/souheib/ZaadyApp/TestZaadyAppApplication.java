package com.souheib.ZaadyApp;

import org.springframework.boot.SpringApplication;

public class TestZaadyAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(ZaadyAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

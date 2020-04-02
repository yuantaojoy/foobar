package com.geek.foobar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FooBar !!
 *
 * @author 曲元涛
 * @date 2020/4/2 20:12
 */
@SpringBootApplication
public class FoobarApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FoobarApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello FooBar");
	}
}

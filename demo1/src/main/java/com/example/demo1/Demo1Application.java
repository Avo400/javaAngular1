package com.example.demo1;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo1Application {
//
// public static void main(String[] args) {
// SpringApplication.run(Demo1Application.class, args);
// }
	EntityManagerFactory entityManagerFactory = null;
	EntityManager entityManager = null;

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

	@Bean
	CommandLineRunner init(MonUser2Repository monUser2Repository) {
		return args -> {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory("javabdd");
				entityManager = entityManagerFactory.createEntityManager();

				List<MonUser2> monUser2s = entityManager.createQuery("from MonUser2", MonUser2.class).getResultList();
				for (MonUser2 monUser2 : monUser2s) {
					monUser2Repository.save(monUser2);

				}
				monUser2Repository.findAll().forEach(System.out::println);

			} catch (Exception e) {
				System.out.println(e);
			} finally {
				entityManager.close();
			}

		};
	}
}
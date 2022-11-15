package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MonUser2Controller {

	// standard constructors
	EntityManagerFactory entityManagerFactory = null;
	EntityManager entityManager = null;
	private final MonUser2Repository monUser2Repository;

	public MonUser2Controller(MonUser2Repository monUser2Repository) {
		this.monUser2Repository = monUser2Repository;
	}

	@GetMapping("/users")
	public List<MonUser2> getUsers() {
		return (List<MonUser2>) monUser2Repository.findAll();
	}

	@PostMapping("/users")
	void addUser(@RequestBody MonUser2 user) {
		try {
			monUser2Repository.save(user);
			insert(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insert(MonUser2 monUser2) {
		System.out.println("- Insertion d'un nouvel objet ----------");
		try {
			EntityTransaction trans = entityManager.getTransaction();
			trans.begin();

			entityManager.persist(monUser2);

			List<MonUser2> results = entityManager.createQuery("from MonUser2", MonUser2.class).getResultList();
			for (MonUser2 result : results) {
				System.out.println(result);
				monUser2Repository.save(result);
			}
			trans.commit();
		} finally {
			if (entityManager != null)
				entityManager.close();
			if (entityManagerFactory != null)
				entityManagerFactory.close();
		}
	}

	public void update(MonUser2 monUser2Upd) {
		Optional<MonUser2> monUser2 = monUser2Repository.findById(monUser2Upd.getId());
		if (monUser2.isPresent()) {
			MonUser2 realMonUser2 = monUser2.get();
			realMonUser2.setName(monUser2Upd.getName());
			realMonUser2.setEmail(monUser2Upd.getEmail());
			EntityTransaction trans = entityManager.getTransaction();
			trans.begin();
			entityManager.persist(realMonUser2);
			List<MonUser2> results = entityManager.createQuery("from MonUser2", MonUser2.class).getResultList();
			for (MonUser2 result : results) {
				System.out.println(result);
				monUser2Repository.save(result);
			}
			trans.commit();
		}

	}

	public void insertOLD(MonUser2 monUser2) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabdd", "loli", "Zanahari72");
			// the mysql insert statement
			String query = " insert into monuser2(name,email)" + " values (?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, monUser2.getName());
			preparedStmt.setString(2, monUser2.getEmail());

			// execute the preparedstatement
			preparedStmt.execute();

			con.close();
		} catch (Exception e) {
			System.err.println("Nahazo an exception!");
			System.err.println(e.getMessage());
		}
	}
}

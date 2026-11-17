package com.tp3.cruddemo;

import com.tp3.cruddemo.dao.StudentDAO;
import com.tp3.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	// Usage : create | read | update [id] | delete [id]   (défaut : read, id = 1)
	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return args -> {
			String action = (args.length > 0) ? args[0] : "read";
			int id = (args.length > 1) ? Integer.parseInt(args[1]) : 1;
			switch (action) {
				case "create" -> createStudent(studentDAO);
				case "read" -> readStudents(studentDAO);
				case "update" -> updateStudent(studentDAO, id);
				case "delete" -> deleteStudent(studentDAO, id);
				case "deleteAll" -> deleteAllStudents(studentDAO);
				default -> System.out.println("Action inconnue: " + action);
			}
		};
	}

	private void createStudent(StudentDAO studentDAO) {
		Student tempStudent = new Student("Paul", "Doe", "paul@test.com");
		studentDAO.save(tempStudent);
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}

	private void readStudents(StudentDAO studentDAO) {
		List<Student> students = studentDAO.findAll();
		System.out.println("All students (" + students.size() + "):");
		students.forEach(System.out::println);

		if (!students.isEmpty()) {
			int firstId = students.get(0).getId();
			System.out.println("Found the student: " + studentDAO.findById(firstId));
		}

		List<Student> does = studentDAO.findByLastName("Doe");
		System.out.println("Students named Doe (" + does.size() + "):");
		does.forEach(System.out::println);
	}

	private void updateStudent(StudentDAO studentDAO, int id) {
		Student myStudent = studentDAO.findById(id);
		if (myStudent == null) {
			System.out.println("No student with id " + id);
			return;
		}
		myStudent.setFirstName("John");
		studentDAO.update(myStudent);
		System.out.println("Updated student: " + studentDAO.findById(id));
	}

	private void deleteAllStudents(StudentDAO studentDAO) {
		int n = studentDAO.deleteAll();
		System.out.println("Deleted row count: " + n);
	}

	private void deleteStudent(StudentDAO studentDAO, int id) {
		studentDAO.delete(id);
		System.out.println("After delete, findById(" + id + ") = " + studentDAO.findById(id));
	}
}

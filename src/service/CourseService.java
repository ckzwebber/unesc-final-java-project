package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import database.dao.CourseDAO;
import database.model.Course;

public class CourseService {

	private static CourseDAO courseDAO;

	static {
		try {
			courseDAO = new CourseDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<Course> list() {
		try {
			return courseDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Course getById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Course course = courseDAO.selectById(id);
			if (course == null) {
				throw new IllegalArgumentException("Course not found");
			}
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Course getByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		try {
			Course course = courseDAO.selectByName(name);
			if (course == null) {
				throw new IllegalArgumentException("Course not found");
			}
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Course course = courseOnDatabase(id);
			if (course == null) {
				throw new IllegalArgumentException("Course not found");
			}
			courseDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Course create(String name, LocalDate processingDate, String startPhase, String endPhase, int sequence,
			String layout) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (processingDate == null) {
			throw new IllegalArgumentException("Processing date cannot be null");
		}
		if (startPhase == null || startPhase.isEmpty()) {
			throw new IllegalArgumentException("Start phase cannot be null or empty");
		}
		if (endPhase == null || endPhase.isEmpty()) {
			throw new IllegalArgumentException("End phase cannot be null or empty");
		}
		if (sequence < 0) {
			throw new IllegalArgumentException("Sequence must be greater than or equal to zero");
		}
		if (layout == null || layout.isEmpty()) {
			throw new IllegalArgumentException("Layout cannot be null or empty");
		}

		Course courseOnDatabase = null;
		try {
			courseOnDatabase = courseDAO.selectByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (courseOnDatabase != null) {
			throw new IllegalArgumentException("Course already exists");
		}

		Course course = new Course(name, processingDate, startPhase, endPhase, sequence, layout);

		try {
			courseDAO.insert(course);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Course update(int id, String name, LocalDate processingDate, String startPhase, String endPhase,
			int sequence, String layout) {

		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (processingDate == null) {
			throw new IllegalArgumentException("Processing date cannot be null");
		}
		if (startPhase == null || startPhase.isEmpty()) {
			throw new IllegalArgumentException("Start phase cannot be null or empty");
		}
		if (endPhase == null || endPhase.isEmpty()) {
			throw new IllegalArgumentException("End phase cannot be null or empty");
		}
		if (sequence < 0) {
			throw new IllegalArgumentException("Sequence must be greater than or equal to zero");
		}
		if (layout == null || layout.isEmpty()) {
			throw new IllegalArgumentException("Layout cannot be null or empty");
		}

		Course courseOnDatabase = courseOnDatabase(id);
		if (courseOnDatabase == null) {
			throw new IllegalArgumentException("Course not found");
		}

		Course course = new Course(id, name, processingDate, startPhase, endPhase, sequence, layout);

		try {
			courseDAO.update(course);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Course courseOnDatabase(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Course course = courseDAO.selectById(id);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

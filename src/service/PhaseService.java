package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.PhaseDAO;
import database.model.Course;
import database.model.Phase;

public class PhaseService {

	private static PhaseDAO phaseDAO;

	static {
		try {
			phaseDAO = new PhaseDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<Phase> list() {
		try {
			return phaseDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Phase getById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			return phaseDAO.selectById(id);
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
			phaseDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Phase create(String name, int courseId) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (courseId <= 0) {
			throw new IllegalArgumentException("Course ID must be greater than zero");
		}

		Phase phase = new Phase();
		phase.setName(name);

		Course course = new Course();
		course.setId(courseId);
		phase.setCourse(course);

		try {
			phaseDAO.insert(phase);
			return phase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Phase update(int id, String name, int courseId) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (courseId <= 0) {
			throw new IllegalArgumentException("Course ID must be greater than zero");
		}

		Phase phaseOnDatabase = phaseOnDatabase(id);
		if (phaseOnDatabase == null) {
			throw new IllegalArgumentException("Phase not found");
		}

		Phase phase = new Phase();
		phase.setId(id);
		phase.setName(name);

		Course course = new Course();
		course.setId(courseId);
		phase.setCourse(course);

		try {
			phaseDAO.update(phase);
			return phase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Phase phaseOnDatabase(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Phase phase = phaseDAO.selectById(id);
			if (phase == null) {
				throw new SQLException("Phase not found");
			}
			return phase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

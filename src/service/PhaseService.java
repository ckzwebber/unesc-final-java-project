package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.PhaseDAO;
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

	public List<Phase> getByCourseId(int courseId) {
		if (courseId <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			return phaseDAO.selectByCourseId(courseId);
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

	public Phase create(String name, int subjectCount, int teacherCount, int courseId) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (subjectCount < 0) {
			throw new IllegalArgumentException("Subject count cannot be negative");
		}
		if (teacherCount < 0) {
			throw new IllegalArgumentException("Teacher count cannot be negative");
		}
		if (courseId < 0) {
			throw new IllegalArgumentException("CourseID cannot be negative");
		}

		Phase phase = new Phase(name, subjectCount, teacherCount, courseId);

		try {
			phaseDAO.insert(phase);
			return phase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Phase update(int id, String name, int subjectCount, int teacherCount, int courseId) {
		if (id < 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (subjectCount < 0) {
			throw new IllegalArgumentException("Subject count cannot be negative");
		}
		if (teacherCount < 0) {
			throw new IllegalArgumentException("Teacher count cannot be negative");
		}
		if (courseId < 0) {
			throw new IllegalArgumentException("CourseID cannot be negative");
		}

		Phase phaseOnDatabase = phaseOnDatabase(id);
		if (phaseOnDatabase == null) {
			throw new IllegalArgumentException("Phase not found");
		}

		Phase phase = new Phase(id, name, subjectCount, teacherCount, courseId);

		try {
			phaseDAO.update(phase);
			return phase;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Phase phaseOnDatabase(int id) {
		if (id < 0) {
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

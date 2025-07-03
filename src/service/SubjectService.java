package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.SubjectDAO;
import database.model.Subject;

public class SubjectService {

	private static SubjectDAO subjectDAO;

	static {
		try {
			subjectDAO = new SubjectDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<Subject> list() {
		try {
			return subjectDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Subject getById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Subject subject = subjectDAO.selectById(id);
			if (subject == null) {
				throw new IllegalArgumentException("Subject not found with the given ID");
			}
			return subject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Subject getByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		try {
			Subject subject = subjectDAO.selectByName(name);
			if (subject == null) {
				throw new IllegalArgumentException("Subject not found with the given name");
			}
			return subject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Subject> getByPhaseId(int phaseId) {
		if (phaseId <= 0) {
			throw new IllegalArgumentException("Phase ID must be greater than zero");
		}

		try {
			List<Subject> subjects = subjectDAO.selectByPhaseId(phaseId);
			if (subjects == null) {
				throw new IllegalArgumentException("No subjects found for the given phase ID");
			}
			return subjects;
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
			Subject subject = subjectDAO.selectById(id);
			if (subject == null) {
				throw new IllegalArgumentException("Subject not found");
			}
			subjectDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Subject create(String code, String name, int weekDay, int teacherQuantity, int phaseId) throws SQLException {
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException("Code cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (weekDay < 0 || weekDay > 7) {
			throw new IllegalArgumentException("Week day must be between 1 and 7");
		}
		if (teacherQuantity < 0) {
			throw new IllegalArgumentException("Teacher quantity cannot be negative");
		}
		if (phaseId <= 0) {
			throw new IllegalArgumentException("Phase ID must be greater than zero");
		}

		Subject subjectOnDatabase = subjectDAO.selectByName(name);
		if (subjectOnDatabase != null) {
			throw new IllegalArgumentException("Discipline with this name already exists");
		}

		Subject subject = new Subject(code, name, weekDay, teacherQuantity, phaseId);

		try {
			subjectDAO.insert(subject);
			Subject createdSubject = subjectDAO.selectByName(name);
			return createdSubject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Subject update(int id, String code, String name, int weekDay, int teacherQuantity, int phaseId) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException("Code cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (weekDay < 1 || weekDay > 7) {
			throw new IllegalArgumentException("Week day must be between 1 and 7");
		}
		if (teacherQuantity < 0) {
			throw new IllegalArgumentException("Teacher quantity cannot be negative");
		}
		if (phaseId <= 0) {
			throw new IllegalArgumentException("Phase ID must be greater than zero");
		}

		Subject subjectOnDatabase = subjectOnDatabase(id);
		if (subjectOnDatabase == null) {
			throw new IllegalArgumentException("Discipline not found");
		}

		Subject subject = new Subject(id, code, name, weekDay, teacherQuantity, phaseId);

		try {
			subjectDAO.update(subject);
			return subject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Subject subjectOnDatabase(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			Subject subject = subjectDAO.selectById(id);
			return subject;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.TeacherDAO;
import database.model.Teacher;

public class TeacherService {

	private static TeacherDAO teacherDAO;

	static {
		try {
			teacherDAO = new TeacherDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<Teacher> list() {
		try {
			return teacherDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Teacher getById(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		try {
			return teacherDAO.selectById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Teacher getByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		try {
			return teacherDAO.selectByName(name);
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
			teacherDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Teacher create(String name, int title) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (title < 0 || title > 4) {
			throw new IllegalArgumentException("Title must be between 0 and 4");
		}

		Teacher existing = teacherOnDatabase(name);

		if (existing != null) {
			throw new IllegalArgumentException("Teacher with this name already exists");
		}

		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setTitle(title);

		try {
			teacherDAO.insert(teacher);
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Teacher update(int id, String name, int title) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}

		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}

		if (title < 0) {
			throw new IllegalArgumentException("Title must be non-negative");
		}

		Teacher teacherOnDatabase = teacherOnDatabase(name);

		if (teacherOnDatabase != null && teacherOnDatabase.getId() != id) {
			throw new IllegalArgumentException("Another teacher with this name already exists");
		}

		Teacher teacher = new Teacher();
		teacher.setId(id);
		teacher.setName(name);
		teacher.setTitle(title);

		try {
			teacherDAO.update(teacher);
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Teacher teacherOnDatabase(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}

		try {
			Teacher teacher = teacherDAO.selectByName(name);
			if (teacher == null) {
				throw new SQLException("Teacher not found");
			}
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

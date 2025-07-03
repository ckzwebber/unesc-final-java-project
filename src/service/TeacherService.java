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
			Teacher teacher = teacherDAO.selectById(id);
			if (teacher == null) {
				throw new IllegalArgumentException("No teacher found with ID: " + id);
			}
			return teacher;
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
			Teacher teacher = teacherDAO.selectByName(name);
			if (teacher == null) {
				throw new IllegalArgumentException("No teacher found with name: " + name);
			}
			return teacher;
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
			Teacher teacher = teacherDAO.selectById(id);
			if (teacher == null) {
				throw new IllegalArgumentException("Teacher not found");
			}
			teacherDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Teacher create(String name, int title, int subjectId) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (title < 0 || title > 4) {
			throw new IllegalArgumentException("Title must be between 0 and 4");
		}
		if (subjectId <= 0) {
			throw new IllegalArgumentException("Subject ID must be greater than zero");
		}

		Teacher teacherOnDatabase = teacherOnDatabase(name);

		if (teacherOnDatabase != null) {
			throw new IllegalArgumentException("Teacher with this name already exists");
		}

		Teacher teacher = new Teacher(name, title, subjectId);

		try {
			teacherDAO.insert(teacher);
			Teacher createdTeacher = teacherDAO.selectByName(name);
			return createdTeacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Teacher update(int id, String name, int title, int subjectId) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID must be greater than zero");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (name.length() < 3) {
			throw new IllegalArgumentException("Name must be at least 3 characters long");
		}
		if (title < 0 || title > 4) {
			throw new IllegalArgumentException("Title must be between 0 and 4");
		}
		if (subjectId <= 0) {
			throw new IllegalArgumentException("Subject ID must be greater than zero");
		}

		Teacher teacherOnDatabase = teacherOnDatabase(name);

		if (teacherOnDatabase == null) {
			throw new IllegalArgumentException("Teacher not found");
		}

		Teacher teacher = new Teacher(id, name, title, subjectId);

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
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

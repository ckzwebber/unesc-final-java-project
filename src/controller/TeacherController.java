package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.Teacher;
import service.TeacherService;

public class TeacherController {

	private static TeacherService teacherService;

	static {
		teacherService = new TeacherService();
	}

	public static List<Teacher> list() throws SQLException {
		List<Teacher> teachers = teacherService.list();
		return teachers;
	}

	public static Teacher getById(int id) throws SQLException {
		Teacher teacher = teacherService.getById(id);
		return teacher;
	}

	public static Teacher getByName(String name) throws SQLException {
		Teacher teacher = teacherService.getByName(name);
		return teacher;
	}

	public static Teacher insert(String name, int title, int subjectId) throws SQLException {
		Teacher teacher = teacherService.create(name, title, subjectId);
		return teacher;
	}

	public static Teacher update(int id, String name, int title, int subjectId) throws SQLException {
		Teacher teacher = teacherService.update(id, name, title, subjectId);
		return teacher;
	}

	public static void delete(int id) throws SQLException {
		teacherService.delete(id);
	}

}

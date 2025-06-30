package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.DisciplineTeacher;
import service.DisciplineTeacherService;

public class DisciplineTeacherController {

	private static DisciplineTeacherService disciplineTeacherService;

	static {
		disciplineTeacherService = new DisciplineTeacherService();
	}

	public static List<DisciplineTeacher> list() throws SQLException {
		List<DisciplineTeacher> disciplineTeachers = disciplineTeacherService.list();
		return disciplineTeachers;
	}

	public static DisciplineTeacher getByDisciplineId(int disciplineId) throws SQLException {
		DisciplineTeacher disciplineTeacher = disciplineTeacherService.getByDisciplineId(disciplineId);
		return disciplineTeacher;
	}

	public static DisciplineTeacher insert(int disciplineId, int teacherId) throws SQLException {
		DisciplineTeacher disciplineTeacher = disciplineTeacherService.create(disciplineId, teacherId);
		return disciplineTeacher;
	}

	public static DisciplineTeacher update(int disciplineId, int teacherId) throws SQLException {
		DisciplineTeacher disciplineTeacher = disciplineTeacherService.update(disciplineId, teacherId);
		return disciplineTeacher;
	}

	public static void delete(int disciplineId, int teacherId) throws SQLException {
		disciplineTeacherService.delete(disciplineId, teacherId);
	}
}

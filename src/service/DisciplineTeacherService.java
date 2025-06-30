package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.DisciplineTeacherDAO;
import database.model.DisciplineTeacher;

public class DisciplineTeacherService {

	private static DisciplineTeacherDAO disciplineTeacherDAO;

	static {
		try {
			disciplineTeacherDAO = new DisciplineTeacherDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<DisciplineTeacher> list() {
		try {
			return disciplineTeacherDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public DisciplineTeacher getByDisciplineId(int disciplineId) {
		if (disciplineId <= 0) {
			throw new IllegalArgumentException("Discipline ID must be greater than zero");
		}

		try {
			return disciplineTeacherDAO.selectByDisciplineId(disciplineId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(int disciplineId, int teacherId) {
		if (disciplineId <= 0) {
			throw new IllegalArgumentException("Discipline ID must be greater than zero");
		}
		if (teacherId <= 0) {
			throw new IllegalArgumentException("Teacher ID must be greater than zero");
		}

		try {
			disciplineTeacherDAO.delete(disciplineId, teacherId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DisciplineTeacher create(int disciplineId, int teacherId) {
		if (disciplineId <= 0) {
			throw new IllegalArgumentException("Discipline ID must be greater than zero");
		}
		if (teacherId <= 0) {
			throw new IllegalArgumentException("Teacher ID must be greater than zero");
		}

		DisciplineTeacher existing = disciplineTeacherOnDatabase(disciplineId);
		if (existing != null) {
			throw new IllegalArgumentException("DisciplineTeacher already exists for this discipline");
		}

		DisciplineTeacher disciplineTeacher = new DisciplineTeacher();
		disciplineTeacher.setDisciplineId(disciplineId);
		disciplineTeacher.setTeacherId(teacherId);

		try {
			disciplineTeacherDAO.insert(disciplineTeacher);
			return disciplineTeacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public DisciplineTeacher update(int disciplineId, int teacherId) {
		if (disciplineId <= 0) {
			throw new IllegalArgumentException("Discipline ID must be greater than zero");
		}
		if (teacherId <= 0) {
			throw new IllegalArgumentException("Teacher ID must be greater than zero");
		}

		DisciplineTeacher disciplineTeacherOnDb = disciplineTeacherOnDatabase(disciplineId);
		if (disciplineTeacherOnDb == null) {
			throw new IllegalArgumentException("DisciplineTeacher not found");
		}

		DisciplineTeacher disciplineTeacher = new DisciplineTeacher();
		disciplineTeacher.setDisciplineId(disciplineId);
		disciplineTeacher.setTeacherId(teacherId);

		try {
			disciplineTeacherDAO.update(disciplineTeacher);
			return disciplineTeacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private DisciplineTeacher disciplineTeacherOnDatabase(int disciplineId) {
		if (disciplineId <= 0) {
			throw new IllegalArgumentException("Discipline ID must be greater than zero");
		}

		try {
			DisciplineTeacher disciplineTeacher = disciplineTeacherDAO.selectByDisciplineId(disciplineId);
			if (disciplineTeacher == null) {
				throw new SQLException("DisciplineTeacher not found");
			}
			return disciplineTeacher;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.Subject;
import service.SubjectService;

public class SubjectController {

    private static SubjectService subjectService;

    static {
        subjectService = new SubjectService();
    }

    public static List<Subject> list() throws SQLException {
        List<Subject> subjects = subjectService.list();
        return subjects;
    }

    public static Subject getById(int id) throws SQLException {
        Subject subject = subjectService.getById(id);
        return subject;
    }

    public static Subject getByName(String name) throws SQLException {
        Subject subject = subjectService.getByName(name);
        return subject;
    }

	public static List<Subject> getByPhaseId(int phaseId) throws SQLException {
        List<Subject> subjects = subjectService.getByPhaseId(phaseId);
        return subjects;
    }

    public static Subject insert(String code, String name, int weekDay, int teacherQuantity, int phaseId) throws SQLException {
        Subject subject = subjectService.create(code, name, weekDay, teacherQuantity, phaseId);
        return subject;
    }

    public static Subject update(int id, String code, String name, int weekDay, int teacherQuantity, int phaseId) throws SQLException {
        Subject subject = subjectService.update(id, code, name, weekDay, teacherQuantity, phaseId);
        return subject;
    }

    public static void delete(int id) throws SQLException {
        subjectService.delete(id);
    }
}

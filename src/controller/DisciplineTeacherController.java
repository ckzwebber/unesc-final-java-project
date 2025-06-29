package controller;

import java.sql.SQLException;
import java.util.List;

import database.dao.DisciplineTeacherDAO;
import database.model.DisciplineTeacher;

public class DisciplineTeacherController {

    private static DisciplineTeacherDAO disciplineTeacherDAO;

    static {
        try {
            disciplineTeacherDAO = new DisciplineTeacherDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static List<DisciplineTeacher> list() throws SQLException {
        return disciplineTeacherDAO.selectAll();
    }

    public static DisciplineTeacher getByDisciplineId(int disciplineId) throws SQLException {
        return disciplineTeacherDAO.selectByDisciplineId(disciplineId);
    }

    public static void insert(int disciplineId, int teacherId) throws SQLException {
        DisciplineTeacher disciplineTeacher = new DisciplineTeacher(disciplineId, teacherId);
        disciplineTeacherDAO.insert(disciplineTeacher);
    }

    public static void update(int disciplineId, int teacherId) throws SQLException {
        DisciplineTeacher disciplineTeacher = new DisciplineTeacher(disciplineId, teacherId);
        disciplineTeacherDAO.update(disciplineTeacher);
    }

    public static void delete(int disciplineId, int teacherId) throws SQLException {
        disciplineTeacherDAO.delete(disciplineId, teacherId);
    }

}

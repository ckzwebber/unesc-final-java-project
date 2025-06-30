package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.Discipline;
import service.DisciplineService;

public class DisciplineController {

    private static DisciplineService disciplineService;

    static {
        disciplineService = new DisciplineService();
    }

    public static List<Discipline> list() throws SQLException {
        List<Discipline> disciplines = disciplineService.list();
        return disciplines;
    }

    public static Discipline getById(int id) throws SQLException {
        Discipline discipline = disciplineService.getById(id);
        return discipline;
    }

    public static Discipline getByName(String name) throws SQLException {
        Discipline discipline = disciplineService.getByName(name);
        return discipline;
    }

    public static List<Discipline> getByPhaseId(int phaseId) throws SQLException {
        List<Discipline> disciplines = disciplineService.getByPhaseId(phaseId);
        return disciplines;
    }

    public static Discipline insert(String code, String name, int weekDay, int phaseId) throws SQLException {
        Discipline discipline = disciplineService.create(code, name, weekDay, phaseId);
        return discipline;
    }

    public static Discipline update(int id, String code, String name, int weekDay, int phaseId) throws SQLException {
        Discipline discipline = disciplineService.update(id, code, name, weekDay, phaseId);
        return discipline;
    }

    public static void delete(int id) throws SQLException {
        disciplineService.delete(id);
    }
}

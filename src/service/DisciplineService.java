package service;

import java.sql.SQLException;
import java.util.List;

import database.dao.DisciplineDAO;
import database.model.Discipline;
import database.model.Phase;

public class DisciplineService {

    private static DisciplineDAO disciplineDAO;

    static {
        try {
            disciplineDAO = new DisciplineDAO();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public List<Discipline> list() {
        try {
            return disciplineDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Discipline getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }

        try {
            return disciplineDAO.selectById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Discipline getByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        try {
            return disciplineDAO.selectByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Discipline> getByPhaseId(int phaseId) {
        if (phaseId <= 0) {
            throw new IllegalArgumentException("Phase ID must be greater than zero");
        }

        try {
            return disciplineDAO.selectByPhaseId(phaseId);
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
            disciplineDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Discipline create(String code, String name, int weekDay, int phaseId) throws SQLException {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (weekDay < 1 || weekDay > 7) {
            throw new IllegalArgumentException("Week day must be between 1 and 7");
        }
        if (phaseId <= 0) {
            throw new IllegalArgumentException("Phase ID must be greater than zero");
        }

        Discipline disciplineOnDatabase = disciplineDAO.selectByName(name);
        if (disciplineOnDatabase != null) {
            throw new IllegalArgumentException("Discipline with this name already exists");
        }

        Discipline discipline = new Discipline();
        discipline.setCode(code);
        discipline.setName(name);
        discipline.setWeekDay(weekDay);
        Phase phase = new Phase();
        phase.setId(phaseId);
        discipline.setPhase(phase);

        try {
            disciplineDAO.insert(discipline);
            return discipline;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Discipline update(int id, String code, String name, int weekDay, int phaseId) {
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
        if (phaseId <= 0) {
            throw new IllegalArgumentException("Phase ID must be greater than zero");
        }

        Discipline disciplineOnDatabase = disciplineOnDatabase(id);
        if (disciplineOnDatabase == null) {
            throw new IllegalArgumentException("Discipline not found");
        }

        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setCode(code);
        discipline.setName(name);
        discipline.setWeekDay(weekDay);
        Phase phase = new Phase();
        phase.setId(phaseId);
        discipline.setPhase(phase);

        try {
            disciplineDAO.update(discipline);
            return discipline;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Discipline disciplineOnDatabase(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }

        try {
            Discipline discipline = disciplineDAO.selectById(id);
            if (discipline == null) {
                throw new SQLException("Discipline not found");
            }
            return discipline;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

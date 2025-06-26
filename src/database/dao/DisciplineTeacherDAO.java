package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.DisciplineTeacher;

public class DisciplineTeacherDAO {

    private static final String SELECT_ALL_QUERY = "SELECT discipline_id, teacher_id FROM tb_disciplines_teachers";
    private static final String SELECT_BY_DISCIPLINE_QUERY = "SELECT discipline_id, teacher_id FROM tb_disciplines_teachers WHERE discipline_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO tb_disciplines_teachers(discipline_id, teacher_id) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE tb_disciplines_teachers SET teacher_id = ? WHERE discipline_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM tb_disciplines_teachers WHERE discipline_id = ? AND teacher_id = ?";

    private final PreparedStatement selectAllStatement;
    private final PreparedStatement selectByDisciplineStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public DisciplineTeacherDAO() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
        selectByDisciplineStatement = connection.prepareStatement(SELECT_BY_DISCIPLINE_QUERY);
        insertStatement = connection.prepareStatement(INSERT_QUERY);
        updateStatement = connection.prepareStatement(UPDATE_QUERY);
        deleteStatement = connection.prepareStatement(DELETE_QUERY);
    }

    public void insert(DisciplineTeacher disciplineTeacher) throws SQLException {
        insertStatement.setInt(1, disciplineTeacher.getDisciplineId());
        insertStatement.setInt(2, disciplineTeacher.getTeacherId());
        insertStatement.executeUpdate();
    }

    public void update(DisciplineTeacher disciplineTeacher) throws SQLException {
        updateStatement.setInt(1, disciplineTeacher.getTeacherId());
        updateStatement.setInt(2, disciplineTeacher.getDisciplineId());
        updateStatement.executeUpdate();
    }

    public void delete(int disciplineId, int teacherId) throws SQLException {
        deleteStatement.setInt(1, disciplineId);
        deleteStatement.setInt(2, teacherId);
        deleteStatement.executeUpdate();
    }

    public ArrayList<DisciplineTeacher> selectAll() throws SQLException {
        ArrayList<DisciplineTeacher> disciplineTeachers = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                DisciplineTeacher disciplineTeacher = buildDisciplineTeacherFromResultSet(resultSet);
                disciplineTeachers.add(disciplineTeacher);
            }
        }
        return disciplineTeachers;
    }

    public DisciplineTeacher selectByDisciplineId(int disciplineId) throws SQLException {
        selectByDisciplineStatement.setInt(1, disciplineId);
        try (ResultSet resultSet = selectByDisciplineStatement.executeQuery()) {
            if (resultSet.next()) {
                DisciplineTeacher disciplineTeacher = buildDisciplineTeacherFromResultSet(resultSet);
                return disciplineTeacher;
            }
        }
        return null;
    }

    private DisciplineTeacher buildDisciplineTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        DisciplineTeacher disciplineTeacher = new DisciplineTeacher();
        disciplineTeacher.setDisciplineId(resultSet.getInt("discipline_id"));
        disciplineTeacher.setTeacherId(resultSet.getInt("teacher_id"));
        return disciplineTeacher;
    }
}

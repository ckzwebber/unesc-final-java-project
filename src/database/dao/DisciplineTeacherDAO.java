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

    public void insert(DisciplineTeacher link) throws SQLException {
        insertStatement.setInt(1, link.getDisciplineId());
        insertStatement.setInt(2, link.getTeacherId());
        insertStatement.executeUpdate();
    }

    public void update(DisciplineTeacher link) throws SQLException {
        updateStatement.setInt(1, link.getTeacherId());
        updateStatement.setInt(2, link.getDisciplineId());
        updateStatement.executeUpdate();
    }

    public void delete(DisciplineTeacher link) throws SQLException {
        deleteStatement.setInt(1, link.getDisciplineId());
        deleteStatement.setInt(2, link.getTeacherId());
        deleteStatement.executeUpdate();
    }

    public ArrayList<DisciplineTeacher> selectAll() throws SQLException {
        ArrayList<DisciplineTeacher> links = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                DisciplineTeacher link = new DisciplineTeacher();
                link.setDisciplineId(resultSet.getInt("discipline_id"));
                link.setTeacherId(resultSet.getInt("teacher_id"));
                links.add(link);
            }
        }
        return links;
    }

    public ArrayList<DisciplineTeacher> selectByDisciplineId(int disciplineId) throws SQLException {
        ArrayList<DisciplineTeacher> links = new ArrayList<>();
        selectByDisciplineStatement.setInt(1, disciplineId);
        try (ResultSet resultSet = selectByDisciplineStatement.executeQuery()) {
            while (resultSet.next()) {
                DisciplineTeacher link = new DisciplineTeacher();
                link.setDisciplineId(resultSet.getInt("discipline_id"));
                link.setTeacherId(resultSet.getInt("teacher_id"));
                links.add(link);
            }
        }
        return links;
    }

    public ArrayList<DisciplineTeacher> select(DisciplineTeacher filter) throws SQLException {
        return selectByDisciplineId(filter.getDisciplineId());
    }
}

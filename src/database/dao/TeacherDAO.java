package database.dao;

import java.sql.*;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Teacher;

public class TeacherDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, name, academic_title, subject_id FROM teachers";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, name, academic_title, subject_id FROM teachers WHERE id = ?";
    private static final String SELECT_BY_NAME_QUERY = "SELECT id, name, academic_title, subject_id FROM teachers WHERE name = ?";
    private static final String SELECT_BY_SUBJECT_ID_QUERY = "SELECT id, name, academic_title, subject_id FROM teachers WHERE subject_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO teachers(name, academic_title, subject_id) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE teachers SET name = ?, academic_title = ?, subject_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM teachers WHERE id = ?";

    private final PreparedStatement selectAllStatement;
    private final PreparedStatement selectByIdStatement;
    private final PreparedStatement selectByNameStatement;
    private final PreparedStatement selectBySubjectIdStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public TeacherDAO() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
        selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        selectByNameStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
        selectBySubjectIdStatement = connection.prepareStatement(SELECT_BY_SUBJECT_ID_QUERY);
        insertStatement = connection.prepareStatement(INSERT_QUERY);
        updateStatement = connection.prepareStatement(UPDATE_QUERY);
        deleteStatement = connection.prepareStatement(DELETE_QUERY);
    }

    public void insert(Teacher teacher) throws SQLException {
        insertStatement.setString(1, teacher.getName());
        insertStatement.setInt(2, teacher.getTitle());
        insertStatement.setInt(3, teacher.getSubjectId());
        insertStatement.executeUpdate();
    }

    public void update(Teacher teacher) throws SQLException {
        updateStatement.setString(1, teacher.getName());
        updateStatement.setInt(2, teacher.getTitle());
        updateStatement.setInt(3, teacher.getSubjectId());
        updateStatement.setInt(4, teacher.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public ArrayList<Teacher> selectAll() throws SQLException {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                teacherList.add(buildTeacherFromResultSet(resultSet));
            }
        }
        return teacherList;
    }

    public Teacher selectById(int id) throws SQLException {
        selectByIdStatement.setInt(1, id);
        try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
            if (resultSet.next()) return buildTeacherFromResultSet(resultSet);
        }
        return null;
    }

    public Teacher selectByName(String name) throws SQLException {
        selectByNameStatement.setString(1, name);
        try (ResultSet resultSet = selectByNameStatement.executeQuery()) {
            if (resultSet.next()) return buildTeacherFromResultSet(resultSet);
        }
        return null;
    }

    public ArrayList<Teacher> selectBySubjectId(int subjectId) throws SQLException {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        selectBySubjectIdStatement.setInt(1, subjectId);
        try (ResultSet resultSet = selectBySubjectIdStatement.executeQuery()) {
            while (resultSet.next()) {
                teacherList.add(buildTeacherFromResultSet(resultSet));
            }
        }
        return teacherList;
    }

    private Teacher buildTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setName(resultSet.getString("name"));
        teacher.setTitle(resultSet.getInt("academic_title"));
        teacher.setSubjectId(resultSet.getInt("subject_id"));
        return teacher;
    }
}

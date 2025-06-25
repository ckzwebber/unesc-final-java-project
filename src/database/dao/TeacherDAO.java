package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Teacher;

public class TeacherDAO {

    private static final String SELECT_ALL_QUERY = "SELECT id, name, title FROM tb_teachers";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, name, title FROM tb_teachers WHERE id = ?";
    private static final String SELECT_BY_NAME_QUERY = "SELECT id, name, title FROM tb_teachers WHERE name = ?";
    private static final String INSERT_QUERY = "INSERT INTO tb_teachers(name, title) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE tb_teachers SET name = ?, title = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM tb_teachers WHERE id = ?";

    private final PreparedStatement selectAllStatement;
    private final PreparedStatement selectByIdStatement;
    private final PreparedStatement selectByNameStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public TeacherDAO() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
        selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        selectByNameStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
        insertStatement = connection.prepareStatement(INSERT_QUERY);
        updateStatement = connection.prepareStatement(UPDATE_QUERY);
        deleteStatement = connection.prepareStatement(DELETE_QUERY);
    }

    public void insert(Teacher teacher) throws SQLException {
        insertStatement.setString(1, teacher.getName());
        insertStatement.setInt(2, teacher.getTitle());
        insertStatement.executeUpdate();
    }

    public void update(Teacher teacher) throws SQLException {
        updateStatement.setString(1, teacher.getName());
        updateStatement.setInt(2, teacher.getTitle());
        updateStatement.setInt(3, teacher.getId());
        updateStatement.executeUpdate();
    }

    public void delete(Teacher teacher) throws SQLException {
        deleteStatement.setInt(1, teacher.getId());
        deleteStatement.executeUpdate();
    }

    public ArrayList<Teacher> selectAll() throws SQLException {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                Teacher teacher = buildTeacherFromResultSet(resultSet);
                teacherList.add(teacher);
            }
        }
        return teacherList;
    }

    public Teacher selectById(int id) throws SQLException {
        selectByIdStatement.setInt(1, id);
        try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
            if (resultSet.next()) {
                Teacher teacher = buildTeacherFromResultSet(resultSet);
                return teacher;
            }
        }
        return null;
    }

    public Teacher selectByName(String name) throws SQLException {
        selectByNameStatement.setString(1, name);
        try (ResultSet resultSet = selectByNameStatement.executeQuery()) {
            if (resultSet.next()) {
                Teacher teacher = buildTeacherFromResultSet(resultSet);
                return teacher;
            }
        }
        return null;
    }

    private Teacher buildTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setName(resultSet.getString("name"));
        teacher.setTitle(resultSet.getInt("title"));
        return teacher;
    }
}

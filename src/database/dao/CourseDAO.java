package database.dao;

import java.sql.*;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Course;

public class CourseDAO {

    private static final String SELECT_ALL_QUERY = "SELECT id, name, processing_date, start_phase, end_phase, sequence, layout_version FROM courses";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, name, processing_date, start_phase, end_phase, sequence, layout_version FROM courses WHERE id = ?";
    private static final String SELECT_BY_NAME_QUERY = "SELECT id, name, processing_date, start_phase, end_phase, sequence, layout_version FROM courses WHERE name = ?";
    private static final String INSERT_QUERY = "INSERT INTO courses(name, processing_date, start_phase, end_phase, sequence, layout_version) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE courses SET name = ?, processing_date = ?, start_phase = ?, end_phase = ?, sequence = ?, layout_version = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM courses WHERE id = ?";

    private final PreparedStatement selectAllStatement;
    private final PreparedStatement selectByIdStatement;
    private final PreparedStatement selectByNameStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    public CourseDAO() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
        selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        selectByNameStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
        insertStatement = connection.prepareStatement(INSERT_QUERY);
        updateStatement = connection.prepareStatement(UPDATE_QUERY);
        deleteStatement = connection.prepareStatement(DELETE_QUERY);
    }

    public void insert(Course course) throws SQLException {
        insertStatement.setString(1, course.getName());
        insertStatement.setDate(2, Date.valueOf(course.getProcessingDate()));
        insertStatement.setString(3, course.getStartPhase());
        insertStatement.setString(4, course.getEndPhase());
        insertStatement.setInt(5, course.getSequence());
        insertStatement.setString(6, course.getLayout());
        insertStatement.executeUpdate();
    }

    public void update(Course course) throws SQLException {
        updateStatement.setString(1, course.getName());
        updateStatement.setDate(2, Date.valueOf(course.getProcessingDate()));
        updateStatement.setString(3, course.getStartPhase());
        updateStatement.setString(4, course.getEndPhase());
        updateStatement.setInt(5, course.getSequence());
        updateStatement.setString(6, course.getLayout());
        updateStatement.setInt(7, course.getId());
        updateStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    public ArrayList<Course> selectAll() throws SQLException {
        ArrayList<Course> courseList = new ArrayList<>();
        try (ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                courseList.add(buildCourseFromResultSet(resultSet));
            }
        }
        return courseList;
    }

    public Course selectById(int id) throws SQLException {
        selectByIdStatement.setInt(1, id);
        try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
            if (resultSet.next()) return buildCourseFromResultSet(resultSet);
        }
        return null;
    }

    public Course selectByName(String name) throws SQLException {
        selectByNameStatement.setString(1, name);
        try (ResultSet resultSet = selectByNameStatement.executeQuery()) {
            if (resultSet.next()) return buildCourseFromResultSet(resultSet);
        }
        return null;
    }

    private Course buildCourseFromResultSet(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setProcessingDate(resultSet.getDate("processing_date").toLocalDate());
        course.setStartPhase(resultSet.getString("start_phase"));
        course.setEndPhase(resultSet.getString("end_phase"));
        course.setSequence(resultSet.getInt("sequence"));
        course.setLayout(resultSet.getString("layout_version"));
        return course;
    }
}
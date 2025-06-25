package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Course;

public class CourseDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, name FROM tb_courses";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, name FROM tb_courses WHERE id = ?";
	private static final String SELECT_BY_NAME_QUERY = "SELECT id, name FROM tb_courses WHERE name = ?";
	private static final String INSERT_QUERY = "INSERT INTO tb_courses(name) VALUES (?)";
	private static final String UPDATE_QUERY = "UPDATE tb_courses SET name = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM tb_courses WHERE id = ?";

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
		insertStatement.executeUpdate();
	}

	public void update(Course course) throws SQLException {
		updateStatement.setString(1, course.getName());
		updateStatement.setInt(2, course.getId());
		updateStatement.executeUpdate();
	}

	public void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}

	public ArrayList<Course> selectAll() throws SQLException {
		ArrayList<Course> courses = new ArrayList<>();
		try (ResultSet rs = selectAllStatement.executeQuery()) {
			while (rs.next()) {
				courses.add(buildCourseFromResultSet(rs));
			}
		}
		return courses;
	}

	public Course selectById(int id) throws SQLException {
		selectByIdStatement.setInt(1, id);
		try (ResultSet rs = selectByIdStatement.executeQuery()) {
			if (rs.next()) {
				return buildCourseFromResultSet(rs);
			}
		}
		return null;
	}

	public Course selectByName(String name) throws SQLException {
		selectByNameStatement.setString(1, name);
		try (ResultSet rs = selectByNameStatement.executeQuery()) {
			if (rs.next()) {
				return buildCourseFromResultSet(rs);
			}
		}
		return null;
	}

	public Course select(Course filter) throws SQLException {
		return selectById(filter.getId());
	}

	private Course buildCourseFromResultSet(ResultSet rs) throws SQLException {
		Course course = new Course();
		course.setId(rs.getInt("id"));
		course.setName(rs.getString("name"));
		return course;
	}
}

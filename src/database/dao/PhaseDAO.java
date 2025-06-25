package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Course;
import database.model.Phase;

public class PhaseDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, name, course_id FROM tb_phases";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, name, course_id FROM tb_phases WHERE id = ?";
	private static final String INSERT_QUERY = "INSERT INTO tb_phases(name, course_id) VALUES (?, ?)";
	private static final String UPDATE_QUERY = "UPDATE tb_phases SET name = ?, course_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM tb_phases WHERE id = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByIdStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement updateStatement;
	private final PreparedStatement deleteStatement;

	public PhaseDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		updateStatement = connection.prepareStatement(UPDATE_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(Phase phase) throws SQLException {
		insertStatement.setString(1, phase.getName());
		insertStatement.setInt(2, phase.getCourse().getId());
		insertStatement.executeUpdate();
	}

	public void update(Phase phase) throws SQLException {
		updateStatement.setString(1, phase.getName());
		updateStatement.setInt(2, phase.getCourse().getId());
		updateStatement.setInt(3, phase.getId());
		updateStatement.executeUpdate();
	}

	public void delete(Phase phase) throws SQLException {
		deleteStatement.setInt(1, phase.getId());
		deleteStatement.executeUpdate();
	}

	public ArrayList<Phase> selectAll() throws SQLException {
		ArrayList<Phase> phaseList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				Phase phase = new Phase();
				phase.setId(resultSet.getInt("id"));
				phase.setName(resultSet.getString("name"));
				Course course = new Course();
				course.setId(resultSet.getInt("course_id"));
				phase.setCourse(course);
				phaseList.add(phase);
			}
		}
		return phaseList;
	}

	public Phase selectById(int id) throws SQLException {
		selectByIdStatement.setInt(1, id);
		try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
			if (resultSet.next()) {
				Phase phase = new Phase();
				phase.setId(resultSet.getInt("id"));
				phase.setName(resultSet.getString("name"));
				Course course = new Course();
				course.setId(resultSet.getInt("course_id"));
				phase.setCourse(course);
				return phase;
			}
		}
		return null;
	}

	public Phase select(Phase phase) throws SQLException {
		return selectById(phase.getId());
	}
}

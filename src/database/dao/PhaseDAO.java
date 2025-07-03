package database.dao;

import java.sql.*;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Phase;

public class PhaseDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, phase_label, subject_count, teacher_count, course_id FROM phases";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, phase_label, subject_count, teacher_count, course_id FROM phases WHERE id = ?";
	private static final String SELECT_BY_COURSE_ID = "SELECT * FROM phases WHERE course_id = ?";
	private static final String SELECT_BY_PHASE_LABEL_AND_COURSE_ID = "SELECT * FROM phases WHERE phase_label = ? AND course_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO phases(phase_label, subject_count, teacher_count, course_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE phases SET phase_label = ?, subject_count = ?, teacher_count = ?, course_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM phases WHERE id = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByIdStatement;
	private final PreparedStatement selectByCourseIdStatement;
	private final PreparedStatement selectByPhaseLabelAndCourseIdStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement updateStatement;
	private final PreparedStatement deleteStatement;

	public PhaseDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
		selectByCourseIdStatement = connection.prepareStatement(SELECT_BY_COURSE_ID);
		selectByPhaseLabelAndCourseIdStatement = connection.prepareStatement(SELECT_BY_PHASE_LABEL_AND_COURSE_ID);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		updateStatement = connection.prepareStatement(UPDATE_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(Phase phase) throws SQLException {
		insertStatement.setString(1, phase.getName());
		insertStatement.setInt(2, phase.getSubjectCount());
		insertStatement.setInt(3, phase.getTeacherCount());
		insertStatement.setInt(4, phase.getCourseId());
		insertStatement.executeUpdate();
	}

	public void update(Phase phase) throws SQLException {
		updateStatement.setString(1, phase.getName());
		updateStatement.setInt(2, phase.getSubjectCount());
		updateStatement.setInt(3, phase.getTeacherCount());
		updateStatement.setInt(4, phase.getCourseId());
		updateStatement.setInt(5, phase.getId());
		updateStatement.executeUpdate();
	}

	public void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}

	public ArrayList<Phase> selectByCourseId(int courseId) throws SQLException {
		ArrayList<Phase> phaseList = new ArrayList<>();
		selectByCourseIdStatement.setInt(1, courseId);
		try (ResultSet resultSet = selectByCourseIdStatement.executeQuery()) {
			while (resultSet.next()) {
				if (resultSet.getInt("course_id") == courseId) {
					phaseList.add(buildPhaseFromResultSet(resultSet));
				}
			}
		}
		return phaseList;
	}

	public Phase selectByPhaseLabelAndCourseId(String phaseLabel, int courseId) throws SQLException {
		selectByPhaseLabelAndCourseIdStatement.setString(1, phaseLabel);
		selectByPhaseLabelAndCourseIdStatement.setInt(2, courseId);
		try (ResultSet resultSet = selectByPhaseLabelAndCourseIdStatement.executeQuery()) {
			if (resultSet.next()) {
				return buildPhaseFromResultSet(resultSet);
			}
		}
		return null;
	}

	public ArrayList<Phase> selectAll() throws SQLException {
		ArrayList<Phase> phaseList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				phaseList.add(buildPhaseFromResultSet(resultSet));
			}
		}
		return phaseList;
	}

	public Phase selectById(int id) throws SQLException {
		selectByIdStatement.setInt(1, id);
		try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
			if (resultSet.next())
				return buildPhaseFromResultSet(resultSet);
		}
		return null;
	}

	private Phase buildPhaseFromResultSet(ResultSet resultSet) throws SQLException {
		Phase phase = new Phase();
		phase.setId(resultSet.getInt("id"));
		phase.setName(resultSet.getString("phase_label"));
		phase.setSubjectCount(resultSet.getInt("subject_count"));
		phase.setTeacherCount(resultSet.getInt("teacher_count"));
		phase.setCourseId(resultSet.getInt("course_id"));
		return phase;
	}
}

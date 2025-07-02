package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Subject;

public class SubjectDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, code, name, weekday, teacher_quantity, phase_id FROM subjects";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, code, name, weekday, teacher_quantity, phase_id FROM subjects WHERE id = ?";
	private static final String SELECT_BY_NAME_QUERY = "SELECT id, code, name, weekday, teacher_quantity, phase_id FROM subjects WHERE name = ?";
	private static final String SELECT_BY_PHASE_ID_QUERY = "SELECT id, code, name, weekday, teacher_quantity, phase_id FROM subjects WHERE phase_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO subjects(code, name, weekday, teacher_quantity, phase_id) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE subjects SET code = ?, name = ?, weekday = ?, teacher_quantity = ?, phase_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM subjects WHERE id = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByIdStatement;
	private final PreparedStatement selectByNameStatement;
	private final PreparedStatement selectByPhaseIdStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement updateStatement;
	private final PreparedStatement deleteStatement;

	public SubjectDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
		selectByNameStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
		selectByPhaseIdStatement = connection.prepareStatement(SELECT_BY_PHASE_ID_QUERY);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		updateStatement = connection.prepareStatement(UPDATE_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(Subject subject) throws SQLException {
		insertStatement.setString(1, subject.getCode());
		insertStatement.setString(2, subject.getName());
		insertStatement.setInt(3, subject.getWeekDay());
		insertStatement.setInt(4, subject.getTeacherQuantity());
		insertStatement.setInt(5, subject.getPhaseId());
		insertStatement.executeUpdate();
	}

	public void update(Subject subject) throws SQLException {
		updateStatement.setString(1, subject.getCode());
		updateStatement.setString(2, subject.getName());
		updateStatement.setInt(3, subject.getWeekDay());
		updateStatement.setInt(4, subject.getPhaseId());
		updateStatement.setInt(5, subject.getId());
		updateStatement.executeUpdate();
	}

	public void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}

	public ArrayList<Subject> selectAll() throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				Subject subject = buildSubjectFromResultSet(resultSet);
				subjectList.add(subject);
			}
		}
		return subjectList;
	}

	public Subject selectById(int id) throws SQLException {
		selectByIdStatement.setInt(1, id);
		try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
			if (resultSet.next()) {
				Subject subject = buildSubjectFromResultSet(resultSet);
				return subject;
			}
		}
		return null;
	}

	public Subject selectByName(String name) throws SQLException {
		selectByNameStatement.setString(1, name);
		try (ResultSet resultSet = selectByNameStatement.executeQuery()) {
			if (resultSet.next()) {
				Subject subject = buildSubjectFromResultSet(resultSet);
				return subject;
			}
		}
		return null;
	}

	public ArrayList<Subject> selectByPhaseId(int phaseId) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<>();
		selectByPhaseIdStatement.setInt(1, phaseId);
		try (ResultSet resultSet = selectByPhaseIdStatement.executeQuery()) {
			while (resultSet.next()) {
				Subject subject = buildSubjectFromResultSet(resultSet);
				subjectList.add(subject);
			}
		}
		return subjectList;
	}

	private Subject buildSubjectFromResultSet(ResultSet resultSet) throws SQLException {
		Subject subject = new Subject();
		subject.setId(resultSet.getInt("id"));
		subject.setCode(resultSet.getString("code"));
		subject.setName(resultSet.getString("name"));
		subject.setWeekDay(resultSet.getInt("weekday"));
		subject.setTeacherQuantity(resultSet.getInt("teacher_quantity"));
		subject.setPhaseId(resultSet.getInt("phase_id"));
		return subject;
	}
}

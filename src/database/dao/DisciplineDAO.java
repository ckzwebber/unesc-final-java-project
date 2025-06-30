package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.Discipline;
import database.model.Phase;

public class DisciplineDAO {

	private static final String SELECT_ALL_QUERY = "SELECT id, code, name, week_day, phase_id FROM tb_disciplines";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, code, name, week_day, phase_id FROM tb_disciplines WHERE id = ?";
	private static final String SELECT_BY_NAME_QUERY = "SELECT id, code, name, week_day, phase_id FROM tb_disciplines WHERE name = ?";
	private static final String SELECT_BY_PHASE_ID_QUERY = "SELECT id, code, name, week_day, phase_id FROM tb_disciplines WHERE phase_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO tb_disciplines(code, name, week_day, phase_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE tb_disciplines SET code = ?, name = ?, week_day = ?, phase_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM tb_disciplines WHERE id = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByIdStatement;
	private final PreparedStatement selectByNameStatement;
	private final PreparedStatement selectByPhaseIdStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement updateStatement;
	private final PreparedStatement deleteStatement;

	public DisciplineDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
		selectByNameStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
		selectByPhaseIdStatement = connection.prepareStatement(SELECT_BY_PHASE_ID_QUERY);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		updateStatement = connection.prepareStatement(UPDATE_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(Discipline discipline) throws SQLException {
		insertStatement.setString(1, discipline.getCode());
		insertStatement.setString(2, discipline.getName());
		insertStatement.setInt(3, discipline.getWeekDay());
		insertStatement.setInt(4, discipline.getPhase().getId());
		insertStatement.executeUpdate();
	}

	public void update(Discipline discipline) throws SQLException {
		updateStatement.setString(1, discipline.getCode());
		updateStatement.setString(2, discipline.getName());
		updateStatement.setInt(3, discipline.getWeekDay());
		updateStatement.setInt(4, discipline.getPhase().getId());
		updateStatement.setInt(5, discipline.getId());
		updateStatement.executeUpdate();
	}

	public void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}

	public ArrayList<Discipline> selectAll() throws SQLException {
		ArrayList<Discipline> disciplineList = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				Discipline discipline = buildDisciplineFromResultSet(resultSet);
				disciplineList.add(discipline);
			}
		}
		return disciplineList;
	}

	public Discipline selectById(int id) throws SQLException {
		selectByIdStatement.setInt(1, id);
		try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
			if (resultSet.next()) {
				Discipline discipline = buildDisciplineFromResultSet(resultSet);
				return discipline;
			}
		}
		return null;
	}

	public Discipline selectByName(String name) throws SQLException {
		selectByNameStatement.setString(1, name);
		try (ResultSet resultSet = selectByNameStatement.executeQuery()) {
			if (resultSet.next()) {
				Discipline discipline = buildDisciplineFromResultSet(resultSet);
				return discipline;
			}
		}
		return null;
	}

	public ArrayList<Discipline> selectByPhaseId(int phaseId) throws SQLException {
		ArrayList<Discipline> disciplineList = new ArrayList<>();
		selectByPhaseIdStatement.setInt(1, phaseId);
		try (ResultSet resultSet = selectByPhaseIdStatement.executeQuery()) {
			while (resultSet.next()) {
				Discipline discipline = buildDisciplineFromResultSet(resultSet);
				disciplineList.add(discipline);
			}
		}
		return disciplineList;
	}

	private Discipline buildDisciplineFromResultSet(ResultSet resultSet) throws SQLException {
		Discipline discipline = new Discipline();
		discipline.setId(resultSet.getInt("id"));
		discipline.setCode(resultSet.getString("code"));
		discipline.setName(resultSet.getString("name"));
		discipline.setWeekDay(resultSet.getInt("week_day"));
		Phase phase = new Phase();
		phase.setId(resultSet.getInt("id_phase"));
		discipline.setPhase(phase);
		return discipline;
	}
}

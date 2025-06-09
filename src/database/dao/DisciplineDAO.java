package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.Discipline;
import database.model.Phase;

public class DisciplineDAO {

	private String selectAll = "SELECT * FROM tb_disciplines";
	private String selectWhere = "SELECT * FROM tb_disciplines WHERE id = ?";
	private String selectWhereName = "SELECT * FROM tb_disciplines WHERE name = ?";
	private String insert = "INSERT INTO tb_disciplines(code, name, week_day, id_phase) VALUES (?, ?, ?, ?)";
	private String update = "UPDATE tb_disciplines SET code = ?, name = ?, week_day = ?, id_phase = ? WHERE id = ?";
	private String delete = "DELETE FROM tb_disciplines WHERE id = ?";

	private PreparedStatement pstSelectAll;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstInsert;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstDelete;
	private PreparedStatement pstSelectWhereName;

	public DisciplineDAO(Connection conn) throws SQLException {
		pstSelectAll = conn.prepareStatement(selectAll);
		pstSelectWhere = conn.prepareStatement(selectWhere);
		pstInsert = conn.prepareStatement(insert);
		pstUpdate = conn.prepareStatement(update);
		pstDelete = conn.prepareStatement(delete);
		pstSelectWhereName = conn.prepareStatement(selectWhereName);
	}

	public void insert(String code, String name, int weekDay, int phaseId) throws SQLException {
		pstInsert.setString(2, code);
		pstInsert.setString(3, name);
		pstInsert.setInt(4, weekDay);
		pstInsert.setInt(5, phaseId);
		pstInsert.execute();
	}

	public void update(String code, String name, int weekDay, int phaseId) throws SQLException {
		pstUpdate.setString(2, code);
		pstUpdate.setString(3, name);
		pstUpdate.setInt(4, weekDay);
		pstUpdate.setInt(5, phaseId);
		pstUpdate.execute();
	}
	
	public void delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		pstDelete.execute();
	}


	public ArrayList<Discipline> selectAll() throws SQLException {
		ArrayList<Discipline> list = new ArrayList<>();
		ResultSet result = pstSelectAll.executeQuery();

		while (result.next()) {
			Discipline discipline = new Discipline();
			discipline.setId(result.getInt("id"));
			discipline.setCode(result.getString("code"));
			discipline.setName(result.getString("name"));
			discipline.setWeekDay(result.getInt("week_day"));

			Phase phase = new Phase();
			phase.setId(result.getInt("id_phase"));
			discipline.setPhase(phase);

			list.add(discipline);
		}

		return list;
	}

	public Discipline selectWhere(int id) throws SQLException {
		Discipline discipline = null;
		pstSelectWhere.setInt(1, id);
		ResultSet result = pstSelectWhere.executeQuery();

		if (result.next()) {
			discipline = new Discipline();
			discipline.setId(result.getInt("id"));
			discipline.setCode(result.getString("code"));
			discipline.setName(result.getString("name"));
			discipline.setWeekDay(result.getInt("week_day"));

			Phase phase = new Phase();
			phase.setId(result.getInt("id_phase"));
			discipline.setPhase(phase);
		}

		return discipline;
	}

	public Discipline selectWhereName(String name) throws SQLException {
		Discipline discipline = null;
		pstSelectWhereName.setString(1, name);
		ResultSet result = pstSelectWhereName.executeQuery();

		if (result.next()) {
			discipline = new Discipline();
			discipline.setId(result.getInt("id"));
			discipline.setCode(result.getString("code"));
			discipline.setName(result.getString("name"));
			discipline.setWeekDay(result.getInt("week_day"));

			Phase phase = new Phase();
			phase.setId(result.getInt("id_phase"));
			discipline.setPhase(phase);
		}

		return discipline;
	}
}

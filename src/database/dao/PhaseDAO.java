package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.Course;
import database.model.Phase;

public class PhaseDAO {

	private String selectAll = "SELECT * FROM tb_phases";
	private String selectWhere = "SELECT * FROM tb_phases WHERE id = ?";
	private String insert = "INSERT INTO tb_phases(name, course_id) VALUES (?, ?)";
	private String update = "UPDATE tb_phases SET name = ?, course_id = ? WHERE id = ?";
	private String delete = "DELETE FROM tb_phases WHERE id = ?";

	private PreparedStatement pstSelectAll;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstInsert;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstDelete;

	public PhaseDAO(Connection conn) throws SQLException {
		pstSelectAll = conn.prepareStatement(selectAll);
		pstSelectWhere = conn.prepareStatement(selectWhere);
		pstInsert = conn.prepareStatement(insert);
		pstUpdate = conn.prepareStatement(update);
		pstDelete = conn.prepareStatement(delete);
	}

	public void insert(String name, int courseId) throws SQLException {
		pstInsert.setString(1, name);
		pstInsert.setInt(2, courseId);
		pstInsert.execute();
	}

	public void delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		pstDelete.execute();
	}

	public void update(String name, int courseId) throws SQLException {
		pstUpdate.setString(1, name);
		pstUpdate.setInt(2, courseId);
		pstUpdate.execute();
	}

	public ArrayList<Phase> selectAll() throws SQLException {

		ArrayList<Phase> phases = new ArrayList<>();
		ResultSet result = pstSelectAll.executeQuery();

		while (result.next()) {
			Phase phase = new Phase();
			phase.setId(result.getInt("id"));
			phase.setName(result.getString("name"));

			Course course = new Course();
			course.setId(result.getInt("course_id"));
			phase.setCourse(course);

			phases.add(phase);
		}

		return phases;
	}

	public Phase selectWhere(int id) throws SQLException {

		Phase phase = null;
		pstSelectWhere.setInt(1, id);
		ResultSet result = pstSelectWhere.executeQuery();

		if (result.next()) {
			phase = new Phase();
			phase.setId(result.getInt("id"));
			phase.setName(result.getString("name"));

			Course course = new Course();
			course.setId(result.getInt("course_id"));
			phase.setCourse(course);
		}

		return phase;
	}
}

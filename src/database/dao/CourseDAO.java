package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.Course;

public class CourseDAO {

	private String selectAll = "SELECT * FROM tb_courses";
	private String selectWhere = "SELECT * FROM tb_courses WHERE id = ?";
	private String selectWhereName = "SELECT * FROM tb_courses WHERE name = ?";
	private String insert = "INSERT INTO tb_courses(name) VALUES (?)";
	private String update = "UPDATE tb_courses SET name = ? WHERE id = ?";
	private String delete = "DELETE FROM tb_courses WHERE id = ?";

	private PreparedStatement pstSelectAll;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstInsert;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstDelete;
	private PreparedStatement pstSelectWhereName;

	public CourseDAO(Connection conn) throws SQLException {
		pstSelectAll = conn.prepareStatement(selectAll);
		pstSelectWhere = conn.prepareStatement(selectWhere);
		pstInsert = conn.prepareStatement(insert);
		pstUpdate = conn.prepareStatement(update);
		pstDelete = conn.prepareStatement(delete);
		pstSelectWhereName = conn.prepareStatement(selectWhereName);
	}

	public void insert(String name) throws SQLException {
		pstInsert.setString(1, name);
		pstInsert.execute();
	}

	public void delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		pstDelete.execute();
	}

	public void update(String name) throws SQLException {
		pstUpdate.setString(1, name);
		pstUpdate.execute();
	}

	public ArrayList<Course> selectAll() throws SQLException {
		ArrayList<Course> list = new ArrayList<>();
		ResultSet rs = pstSelectAll.executeQuery();
		while (rs.next()) {
			Course course = new Course();
			course.setId(rs.getInt("id"));
			course.setName(rs.getString("name"));
			list.add(course);
		}
		return list;
	}

	public Course selectWhere(int id) throws SQLException {
		Course course = null;
		pstSelectWhere.setInt(1, id);
		ResultSet rs = pstSelectWhere.executeQuery();
		if (rs.next()) {
			course = new Course();
			course.setId(rs.getInt("id"));
			course.setName(rs.getString("name"));
		}
		
		return course;
	}

	public Course selectWhereName(String name) throws SQLException {
		Course course = null;
		pstSelectWhereName.setString(1, name);
		ResultSet rs = pstSelectWhereName.executeQuery();
		if (rs.next()) {
			course = new Course();
			course.setId(rs.getInt("id"));
			course.setName(rs.getString("name"));
		}
		
		return course;
	}
}

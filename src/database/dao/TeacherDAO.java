package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.Teacher;

public class TeacherDAO {
    
    public String selectAll = "SELECT * FROM tb_teachers";
    public String selectWhereName = "SELECT * FROM tb_teachers WHERE name = ?";
    public String insert = "INSERT INTO tb_teachers(name, title) VALUES (?, ?)";
    public String update = "UPDATE tb_teachers SET name = ?, title = ? WHERE id = ?";
    public String delete = "DELETE FROM tb_teachers WHERE id = ?";

    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectWhereName;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public TeacherDAO(Connection conn) throws SQLException {
        pstSelectAll = conn.prepareStatement(selectAll);
        pstSelectWhereName = conn.prepareStatement(selectWhereName);
        pstInsert = conn.prepareStatement(insert);
        pstUpdate = conn.prepareStatement(update);
        pstDelete = conn.prepareStatement(delete);
    }

    public void insert(String name, String title) throws SQLException {
        pstInsert.setString(1, name);
        pstInsert.setString(2, title);
        pstInsert.execute();
    }

    public void delete(int id) throws SQLException {
        pstDelete.setInt(1, id);
        pstDelete.execute();
    }

    public void update(String name, String title) throws SQLException {
        pstUpdate.setString(2, name);
        pstUpdate.setString(3, title);
        pstUpdate.execute();
    }

    public ArrayList<Teacher> selectAll() throws SQLException {
        
        ArrayList<Teacher> teachers = new ArrayList<>();
        
        ResultSet result = pstSelectAll.executeQuery();
        
        while (result.next()) {
            Teacher teacher = new Teacher();
            teacher.setId(result.getInt("id"));
            teacher.setName(result.getString("name"));
            teacher.setTitle(result.getInt("title"));
            teachers.add(teacher);     
        }
        
        return teachers;
    }

    public Teacher selectWhereName(String name) throws SQLException {
        
        Teacher teacher = null;
        pstSelectWhereName.setString(1, name);
        
        ResultSet result = pstSelectWhereName.executeQuery();
        if (result.next()) {
            teacher = new Teacher();
            teacher.setId(result.getInt("id"));
            teacher.setName(result.getString("name"));
            teacher.setTitle(result.getInt("title"));
        }
        
        return teacher;
    }

}

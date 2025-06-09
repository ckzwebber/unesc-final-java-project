package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.model.DisciplineTeacher;

public class DisciplineTeacherDAO {
    
    private String selectAll = "SELECT * FROM tb_disciplines_teachers";
    private String selectWhere = "SELECT * FROM tb_disciplines_teachers WHERE discipline_id = ?";
    private String insert = "INSERT INTO tb_disciplines_teachers(discipline_id, teacher_id) VALUES (?, ?)";
    private String delete = "DELETE FROM tb_disciplines_teachers WHERE discipline_id = ? AND teacher_id = ?";
    private String update = "UPDATE tb_disciplines_teachers SET teacher_id = ? WHERE discipline_id = ?";

    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectWhere;
    private PreparedStatement pstInsert;
    private PreparedStatement pstDelete;
    private PreparedStatement pstUpdate;

    public DisciplineTeacherDAO(Connection conn) throws SQLException {
        pstSelectAll = conn.prepareStatement(selectAll);
        pstSelectWhere = conn.prepareStatement(selectWhere);
        pstInsert = conn.prepareStatement(insert);
        pstDelete = conn.prepareStatement(delete);
        pstUpdate = conn.prepareStatement(update);
    }

    public void insert(int disciplineId, int teacherId) throws SQLException {
        pstInsert.setInt(1, disciplineId);
        pstInsert.setInt(2, teacherId);
        pstInsert.execute();
    }

    public void delete(int disciplineId, int teacherId) throws SQLException {
        pstDelete.setInt(1, disciplineId);
        pstDelete.setInt(2, teacherId);
        pstDelete.execute();
    }

    public void update(int disciplineId, int teacherId) throws SQLException {
        pstUpdate.setInt(1, teacherId);
        pstUpdate.setInt(2, disciplineId);
        pstUpdate.execute();
    }

    public ArrayList<DisciplineTeacher> selectAll() throws SQLException {
        ArrayList<DisciplineTeacher> listDT = new ArrayList<>();
        ResultSet result = pstSelectAll.executeQuery();
        
        while (result.next()) {
            DisciplineTeacher disciplineTeacher = new DisciplineTeacher();
            disciplineTeacher.setDisciplineId(result.getInt("discipline_id"));
            disciplineTeacher.setTeacherId(result.getInt("teacher_id"));
            listDT.add(disciplineTeacher);
        }
        
        return listDT;
    }

    public ArrayList<DisciplineTeacher> selectWhere(int disciplineId) throws SQLException {
        ArrayList<DisciplineTeacher> listDT = new ArrayList<>();
        pstSelectWhere.setInt(1, disciplineId);
        ResultSet result = pstSelectWhere.executeQuery();
        
        while (result.next()) {
            DisciplineTeacher disciplineTeacher = new DisciplineTeacher();
            disciplineTeacher.setDisciplineId(result.getInt("discipline_id"));
            disciplineTeacher.setTeacherId(result.getInt("teacher_id"));
            listDT.add(disciplineTeacher);
        }
        
        return listDT;
    }
}

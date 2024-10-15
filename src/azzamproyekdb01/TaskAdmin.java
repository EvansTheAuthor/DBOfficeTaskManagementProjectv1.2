/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;

public class TaskAdmin {
    private Connection conn;
    private TransactionManager transMan;

    //mengatur tugas baru
    public void setNewTask(Long task_id, String task_name, String status, Long assigned_to, Long assigned_by, Date deadline) throws SQLException {
    String sql = "INSERT INTO TaskList(task_id, task_name, status, assigned_to, assigned_by, deadline) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;

    try {
        if (transMan == null) {
            transMan = new TransactionManager();  // Inisialisasi transMan
        }
        conn = DBConnection.getConnection();
        transMan.begin();
        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, task_id);
        pstmt.setString(2, task_name);
        pstmt.setString(3, status);
        pstmt.setLong(4, assigned_to);
        pstmt.setLong(5, assigned_by);
        pstmt.setDate(6, deadline);

        System.out.println("Menjalankan query: " + pstmt.toString());
        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            transMan.commit();
            System.out.println("Data berhasil ditambahkan ke tabel TaskList.");
        } else {
            transMan.rollback();
            System.out.println("Tidak ada baris yang ditambahkan.");
        }
    } catch (SQLException ex) {
        transMan.rollback();
        JOptionPane.showMessageDialog(null, "Error saat menambahkan tugas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

    //mengatur status tugass
    public void setTaskStatus(String status, long task_id) throws SQLException{
        String sql="update TaskList set status=? where task_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setString(1, status);
            pstmt.setLong(2, task_id);

            int affectedRows=pstmt.executeUpdate();
            if(affectedRows>0){
                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException ex){
            transMan.rollback();
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    //mengubah tim penugasan tugas
    public void setTaskHandler(String assigned_to, String task_id)throws SQLException{
        String sql="update TaskList set assigned_to=? where task_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, Long.parseLong(assigned_to));
            pstmt.setLong(2, Long.parseLong(task_id));

            int affectedRows=pstmt.executeUpdate();

            if(affectedRows>0){
                transMan.commit();
            }else{
                transMan.rollback();
            }
        } catch(SQLException ex){
            transMan.rollback();
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    //menghapus tugas
    public void dropTask(int task_id) throws SQLException{
        String sql="delete from TaskList where task_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setInt(1, task_id);

            int affectedRows=pstmt.executeUpdate();
            if(affectedRows>0){
                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException ex){
            transMan.rollback();
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    //menapilkan tabel TaskList
    public List<Task> getTasks() throws SQLException{
        List<Task> tasks=new ArrayList<>();
        String sql="select * from TaskList";

        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            conn=DBConnection.getConnection();
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()){
                Task task=new Task();
                task.setTask_id(rs.getLong("task_id"));
                task.setTask_name(rs.getString("task_name"));
                task.setStatus(rs.getString("status"));
                task.setAssigned_to(rs.getLong("assigned_to"));
                task.setAssigned_by(rs.getLong("assigned_by"));
                task.setDeadline(rs.getDate("deadline"));

                tasks.add(task);
            }
            
        }catch (SQLException ex){
            System.out.println("Error while fetching taks --> "+ex);
            throw ex;
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
        return tasks;
    }
}
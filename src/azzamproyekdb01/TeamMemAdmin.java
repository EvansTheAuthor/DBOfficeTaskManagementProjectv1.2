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
/**
 *
 * @author Guest Prodas
 */
public class TeamMemAdmin {
    private Connection conn;
    private TransactionManager transMan;

    //menambah anggota tim
    public void addMember(long team_id, long employee_id) throws SQLException{
        String sql="insert into TeamMemberList (team_id, employee_id) values(?,?)";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, team_id);
            pstmt.setLong(2, employee_id);

            pstmt.executeUpdate();
            transMan.commit();
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

    //menghapus anggota tim
    public void deleteMember(long team_id, long employee_id) throws SQLException{
        String sql="delete from TeamMemberList where team_id=? and employee_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, team_id);
            pstmt.setLong(2, employee_id);

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

    //mencari anggota tim dengan ID tim
    public List<TeamMembers> getTeamMembers(long team_id) throws SQLException{
        List<TeamMembers> teamMemList=new ArrayList<>();
        String sql="select tm.team_id, tm.employee_id, e.employee_name"+
        " from TeamMemberList tm join EmployeeList e on tm.employee_id=e.employee_id"
        +"where tm.team_id=?";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setLong(1, team_id);
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()){
                TeamMembers teamMem=new TeamMembers();

                teamMem.setTeam_id(rs.getLong("team_id"));
                teamMem.setEmployee_id(rs.getLong("employee_id"));

                teamMemList.add(teamMem);
            }
        }catch(SQLException ex){
            System.out.println("Error while fetcing table of Team Members --> "+ex.getMessage());
            throw ex;
        }
        return teamMemList;
    }
}

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
public class TeamAdmin {
    private Connection conn;
    private TransactionManager transMan;

    public void setNewTeam(long team_id, String team_name, long manager_id)throws SQLException{
        String sql="insert into TeamList(team_id, team_name, manager_id) values(?,?,?)";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, team_id);
            pstmt.setString(2, team_name);
            pstmt.setLong(3, manager_id);

            pstmt.executeUpdate();
            transMan.commit();
        }catch(SQLException ex){
            transMan.rollback();
            throw new SQLException("Error saat menambahkan tim: " + ex.getMessage());
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    public void setNewTeamName(String team_name, String manager_id) throws SQLException{
        String sql="update TeamList set team_name=? where manager_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setString(1, team_name);
            pstmt.setLong(2, Long.parseLong("manager_id"));
            
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

    public void setNewTeamManager(long manager_id, long team_id)throws SQLException{
        String sql="update TeamList set manager_id=? where team_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, manager_id);
            pstmt.setLong(2, team_id);

            int affectedRows=pstmt.executeUpdate(sql);
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

    public void dropTeam(long team_id) throws SQLException{
        String sql="delete from TeamList where team_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, team_id);
            
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

    public List<Team> getTeams() throws SQLException{
        List<Team> teams=new ArrayList<>();
        String sql="select * from TeamList";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()){
                Team team=new Team();
                team.setTeam_id(rs.getLong("team_id"));
                team.setTeam_name(rs.getString("team_name"));
                team.setManager_id(rs.getLong("manager_id"));

                teams.add(team);
            }
        }catch(SQLException ex){
            System.out.println("Error while fetching teams --> "+ex.getMessage());
            throw ex;
        }
        return teams;
    }
}

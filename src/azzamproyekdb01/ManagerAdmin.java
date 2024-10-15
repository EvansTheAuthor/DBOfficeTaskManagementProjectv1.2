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
 * @author Guest OSN
 */
public class ManagerAdmin {
    private Connection conn;
    private TransactionManager transMan;

    //mengatur
    public void setManager(int manager_id, String name) throws SQLException{
        String sqlGetEmployeeId="select employee_id from EmployeeList where name=?";
        String sqlUpdatePosition="update EmployeeList set position='Manager' where employee_id=?";
        String sqlInsertManager="insert into ManagerList(manager_id, employee_id) values(?, ?)";

        PreparedStatement pstmtGetEmployeeId=null;
        PreparedStatement pstmtUpdatePosition=null;
        PreparedStatement pstmtInsertManager=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();

            pstmtGetEmployeeId=conn.prepareStatement(sqlGetEmployeeId);
            pstmtGetEmployeeId.setString(1, name);
            ResultSet rs=pstmtGetEmployeeId.executeQuery();

            if((rs.next())){
                long employee_id=rs.getLong("employee_id");

                pstmtUpdatePosition=conn.prepareStatement(sqlUpdatePosition);
                pstmtUpdatePosition.setLong(1, employee_id);
                pstmtUpdatePosition.executeUpdate();

                pstmtInsertManager=conn.prepareStatement(sqlInsertManager);
                pstmtInsertManager.setInt(1, manager_id);
                pstmtInsertManager.setLong(2, employee_id);
                pstmtInsertManager.executeUpdate();

                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException ex){
            transMan.rollback();
        }finally{
            if(pstmtGetEmployeeId!=null){
                pstmtGetEmployeeId.close();
            }

            if(pstmtUpdatePosition!=null){
                pstmtUpdatePosition.close();
            }
            if(pstmtInsertManager!=null){
                pstmtInsertManager.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    //menghapus jabatan manajer alias menurunkan pangkat karyawan
    public void dropManager(int manager_id)throws SQLException{
        String sqlGetEmployeeId="select employee_id from ManagerList where manager_id=?";
        String sqlUpdatePosition="update EmployeeList set position='Employee' where employee_id=?";
        String sqlDeleteManager="delete from ManagerList where manager_id=?";

        PreparedStatement pstmtGetEmployeeId=null;
        PreparedStatement pstmtUpdatePosition=null;
        PreparedStatement pstmtDeleteManager=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
           transMan.begin();
           
           //mengambil employe_id dari ManagerList berdasar manager_id
           pstmtGetEmployeeId=conn.prepareStatement(sqlGetEmployeeId);
            pstmtGetEmployeeId.setInt(1, manager_id);
            ResultSet rs=pstmtGetEmployeeId.executeQuery();

            if(rs.next()){
                long employee_id=rs.getLong("employee_id");

                //memperbarui posisi karyawan menjadi employee
                pstmtUpdatePosition=conn.prepareStatement(sqlUpdatePosition);
                pstmtUpdatePosition.setLong(1, employee_id);
                pstmtUpdatePosition.executeUpdate();

                //menghapus data manajer dari ManajerList
                pstmtDeleteManager=conn.prepareStatement(sqlDeleteManager);
                pstmtDeleteManager.setInt(1, manager_id);
                pstmtDeleteManager.executeUpdate();

                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException ex){
            transMan.rollback();
        }finally{
            if(pstmtGetEmployeeId!=null){
                pstmtGetEmployeeId.close();
            }
            if(pstmtUpdatePosition!=null){
                pstmtUpdatePosition.close();
            }
            if(pstmtDeleteManager!=null){
                pstmtDeleteManager.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    //menampilkan tabel manajer
    public List<Manager> Managers() throws SQLException{
        List<Manager> managers=new ArrayList<>();

        String sql="select m.manager_id, m.employee_id, e.name, e.position"+
        "from ManagerList m join EmployeeList e on m.employee_id=e.employee_id";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()){
                Manager manager=new Manager();
                manager.setManager_id(rs.getLong("manager_id"));
                manager.setEmployee_id(rs.getLong("employee_id"));
                manager.setName(rs.getString("name"));
                manager.setPosition(rs.getString("positon"));

                managers.add(manager);
            }
        }catch(SQLException ex){
            System.out.println("Error while fetching managers --> "+ex.getMessage());
            throw ex;
        }
        return managers;
    }
}

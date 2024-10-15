/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author GUEST DB
 */
public class EmployeeAdmin {
    private Connection conn;
    private TransactionManager transMan;
    
    public void addEmployee(String employee_id, String name, String position, String address, String email, String phone) throws SQLException{
        
        String sql="insert into EmployeeList(employee_id, name, position, address, email, phone) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt=null;
        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            pstmt=conn.prepareStatement(sql);
            transMan.begin();
            
            pstmt.setLong(1, Long.parseLong(employee_id));
            pstmt.setString(2, name);
            pstmt.setString(3, position);
            pstmt.setString(4, address);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            
            pstmt.executeUpdate();
            transMan.commit();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            transMan.rollback();
        }catch(NumberFormatException ex){
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

    public void updateEmployee(String employee_id, String name, String position, String address, String email, String phone) throws SQLException{
        String sql="update EmployeeList SET name = ?, position = ?, address = ?, email = ?, phone = ? WHERE employee_id = ?";
        PreparedStatement pstmt=null;
        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            pstmt=conn.prepareStatement(sql);
            transMan.begin();
            
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setString(3, address);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setLong(6, Long.parseLong(employee_id));

            int affectedRows=pstmt.executeUpdate();

            if(affectedRows>0){
                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException|NumberFormatException ex){
            transMan.rollback();
            throw ex;
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    public void deleteEmployee(String employee_id) throws SQLException{
        String sql="delete from EmployeeList where employee_id=?";
        PreparedStatement pstmt=null;

        try{
            if (transMan == null) {
                transMan = new TransactionManager();  // Inisialisasi transMan
            }
            conn=DBConnection.getConnection();
            transMan.begin();
            pstmt=conn.prepareStatement(sql);

            pstmt.setLong(1, Long.parseLong(employee_id));

            int affectedRows=pstmt.executeUpdate();

            if(affectedRows>0){
                transMan.commit();
            }else{
                transMan.rollback();
            }
        }catch(SQLException|NumberFormatException ex){
                transMan.rollback();
                throw ex;
        }finally{
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    public List<Employee> getEmployees() throws SQLException{
        List<Employee> employees=new ArrayList<>();
        String sql="select * from EmployeeList";

        try{
            conn=DBConnection.getConnection();
            try(PreparedStatement pstmt=conn.prepareStatement(sql);
                ResultSet rs=pstmt.executeQuery()){
            while(rs.next()){
                Employee emp=new Employee();
                emp.setEmployee_id(rs.getLong("employee_id"));
                emp.setName(rs.getString("name"));
                emp.setPosition(rs.getString("position"));
                emp.setAddress(rs.getString("address"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
                employees.add(emp);
            }
        }catch(SQLException ex){
            System.out.println("Error while fetching employees --> "+ex.getMessage());
            throw ex;
        }
        }catch(SQLException ex){
            System.out.println("Error while connecting to database  --> "+ex.getMessage());
            throw ex;
        }
        finally{
            if(conn!=null){
                conn.close();
            }
        }
        return employees;
    }
}

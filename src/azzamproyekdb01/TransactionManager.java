/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author GUEST DB
 */
public class TransactionManager {
    private Connection conn=null;
    
    public void begin() throws SQLException{
        if(conn!=null){
            conn.setAutoCommit(false);
        }
    }
    
    public void commit() throws SQLException{
        if(conn!=null){
            conn.commit();
            conn.setAutoCommit(true);
        }
    }
    
    public void rollback() throws SQLException{
        if(conn!=null){
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }
    
    public void closeConnection() throws SQLException{
        if(conn!=null&&!conn.isClosed()){
            conn.close();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;
import java.sql.Date;
public class Task {
    private long task_id;
    private String task_name;
    private String status;
    private long assigned_to;
    private long assigned_by;
    private Date deadline;

    public long getTask_id() {
        return task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getStatus() {
        return status;
    }

    public long getAssigned_to() {
        return assigned_to;
    }

    public long getAssigned_by() {
        return assigned_by;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssigned_to(long assigned_to) {
        this.assigned_to = assigned_to;
    }

    public void setAssigned_by(long assigned_by) {
        this.assigned_by = assigned_by;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}

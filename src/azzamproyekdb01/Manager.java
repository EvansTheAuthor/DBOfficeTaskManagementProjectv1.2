/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;

/**
 *
 * @author Guest OSN
 */
public class Manager extends Employee{
    private long manager_id;

    public long getManager_id() {
        return manager_id;
    }

    public void setManager_id(long manager_id) {
        this.manager_id = manager_id;
    }

    @Override
    public long getEmployee_id() {
        return super.getEmployee_id();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azzamproyekdb01;

/**
 *
 * @author Guest OSN
 */
public class Team extends Manager{
    private Long team_id;
    private String team_name;
    private String manager_name;
    private Long manager_id;
    
    public Team(Long team_id, String team_name, Long manager_id){
        this.team_id=team_id;
        this.team_name=team_name;
        this.manager_id=manager_id;
    }
    
    public Team(){}

    public Long getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }
    
    public String getManager_name(){
        return manager_name;
    }

    @Override
    public long getManager_id() {
        // TODO Auto-generated method stub
        return super.getManager_id();
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    @Override
    public void setManager_id(long manager_id) {
        // TODO Auto-generated method stub
        super.setManager_id(manager_id);
    }
    
    public void setManager_name(String manager_name){
        super.setName(manager_name);
    }
}
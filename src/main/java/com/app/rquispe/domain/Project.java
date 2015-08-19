package com.app.rquispe.domain;

import javax.persistence.*;

/**
 * Created by ruben on 8/18/15.
 */
@Entity
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    @Column
    private String name;
    @Column
    private String projectDescription;

    //reverse relationShip
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID") //give a name for the column mapped
    private Employee employee;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String description) {
        this.projectDescription = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

package com.app.rquispe.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ruben on 8/14/15.
 */
@Entity(name = "T_EMPLOYEE") //creates the table with the name T_EMPLOYEE AND ALSO CHANGE THE NAME OF THE ENTITY
@Table(name = "EMPLOYEE") //ONLY CREATES THE NAME OF THE TABLE
public class Employee {

    public Employee() {
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)//GenerationType.AUTO hibernate makes a decision what to use
    //GenerationType.Identity hibernate will use identity columns a feature of the database.
    // GenerationType.Sequence use sequence object, GenerationType.Table you can have a separate table in order
    // to generate a primary key
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Basic
    @Column(name = "salary")
    private double salary;

    @Transient //skip this value to persist
    private String optional;

    @Temporal(TemporalType.DATE)  //si no se pone sta anotacion el campo se guarda como timestamp en BD ahora se guarda como date
    private Date joinDate;

    @Lob  //a large object more than 255 characteres
    private String descriptio;

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

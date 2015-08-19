package com.app.rquispe.domain;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ruben on 8/14/15.
 */
@Entity(name = "T_EMPLOYEE") //creates the table with the name T_EMPLOYEE AND ALSO CHANGE THE NAME OF THE ENTITY
@Table(name = "EMPLOYEE") //ONLY CREATES THE NAME OF THE TABLE
public class Employee {

    public Employee() {
    }

    //@EmbeddedId
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
    private String description;

    @OneToOne
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")  //to avoid creating another table we mapped with the entity but we need to remove join anotation here
    /*@JoinTable(name = "USER_PROJECT", joinColumns = @JoinColumn(name = "USER_ID"), //koinColumn is the id of this entity
    inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")) */ //is the id  of the project entity
    private Set<Project> projects = new HashSet<>();

    //mark this collection to be persistent by hibernate creates a table called Employee_listOfAddresses
    @ElementCollection(fetch = FetchType.EAGER)
    //Avoid the default name of the table
    //Address is not an entity is a value type
    @JoinTable(name = "EMPLOYEE_ADDRESS",
                joinColumns = @JoinColumn(name = "EMPLOYEE_ID")) //change de name of de id(FK) column in this table
    private Collection<Address> listOfAddresses = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Address> getListOfAddresses() {
        return listOfAddresses;
    }

    public void setListOfAddresses(Collection<Address> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
    }

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}

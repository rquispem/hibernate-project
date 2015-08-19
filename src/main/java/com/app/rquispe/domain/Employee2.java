package com.app.rquispe.domain;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ruben on 8/14/15.
 */
//@Entity(name = "T_EMPLOYEE") //creates the table with the name T_EMPLOYEE AND ALSO CHANGE THE NAME OF THE ENTITY
//@Table(name = "EMPLOYEE") //ONLY CREATES THE NAME OF THE TABLE
public class Employee2 {

    public Employee2() {
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
    private String descriptio;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET_NAME")),
        @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY_NAME")),
        @AttributeOverride(name = "state", column = @Column(name = "HOME_STATE_NAME")),
        @AttributeOverride(name = "pincode", column = @Column(name = "HOME_PINCODE_NAME"))
    })
    private Address homeAddress;

    @Embedded
    private Address officeAddress;

    //mark this collection to be persistent by hibernate creates a table called Employee_listOfAddresses
    @ElementCollection
    //Avoid the default name of the table
    @JoinTable(name = "USER_ADDRESS",
                joinColumns = @JoinColumn(name = "USER_ID")) //change de name of de id(FK) column in this table
    private Set<Address> listOfAddresses = new HashSet<>();


    @ElementCollection
    @JoinTable(name = "USER_ADDRESS",
            joinColumns = @JoinColumn(name = "USER_ID")) //change de name of de id(FK) column in this table
    @GenericGenerator(name = "hilo-gen", strategy = "hilo")
    @CollectionId(columns = {@Column(name = "ADRESS_ID")}, generator = "hilo-gen", type = @Type(type = "long"))
    private Collection<Address> listOfAddresses2 = new ArrayList<>(); //to create a primary key for address


    public Set<Address> getListOfAddresses() {
        return listOfAddresses;
    }

    public void setListOfAddresses(Set<Address> listOfAddresses) {
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
}

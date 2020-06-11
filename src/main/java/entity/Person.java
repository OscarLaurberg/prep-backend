/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import dtos.PersonDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author oscar
 */
@Entity
@Table(name = "Person")
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person"),
    @NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.getByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
    @NamedQuery(name = "Person.deletePerson", query = "DELETE FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = "Person.getByHobbyCount", query = "SELECT COUNT(p) FROM Person p JOIN p.hobbies h WHERE h.name = :hobby"),
    @NamedQuery(name = "Person.getByHobby", query = "SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobby"),
    @NamedQuery(name = "Person.getByPhone", query = "SELECT p FROM Person p WHERE p.phone = :phone")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column (nullable = false)
    private String phone;
    @Column (nullable = false)
    private String email;
    
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinTable(
            name = "PersonHobby",
            joinColumns = {
                @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "HOBBY_ID", referencedColumnName = "ID" )}
    )
    private List<Hobby> hobbies = new ArrayList<>();
    
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Address address;
    
    public Person(){
        
    }
    public Person (String firstName, String lastName, String phone, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
    
    public Person (PersonDTO pers){
        this.firstName = pers.getFirstName();
        this.lastName = pers.getLastName();
        this.phone = pers.getPhone();
        this.email = pers.getEmail();
        if(pers.getAddress()!=null){
            this.address = new Address (pers.getAddress());
            
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addHobby(Hobby hobby){
        hobby.addPerson(this);
        if (!hobbies.contains(hobby)){
            hobbies.add(hobby);
        }}

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + phone +'}';
    }
    
    
}

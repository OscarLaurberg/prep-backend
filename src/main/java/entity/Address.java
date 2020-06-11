/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import dtos.AddressDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author oscar
 */
@Entity
@Table(name = "Address")
@NamedQueries({
    @NamedQuery(name = "Address.deleteAllRows", query = "DELETE FROM Address"),
    @NamedQuery(name = "Address.getAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.getByStreet", query = "SELECT a FROM Address a WHERE a.street LIKE :street")
})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String street;
    @Column (nullable = false)
    private int zip;
    @Column (nullable = false)
    private String city;
    
    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> persons = new ArrayList<>();
    
    public Address(){
        
    }
    public Address(String street, int zip, String city){
        this.street=street;
        this.zip=zip;
        this.city=city;
        
    }
    
    public Address(AddressDTO addressDTO){
        this.id = addressDTO.getId();
        this.street = addressDTO.getStreet();
        this.zip = addressDTO.getZip();
        this.city = addressDTO.getCity();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    public void addPerson (Person person){
        persons.add(person);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}

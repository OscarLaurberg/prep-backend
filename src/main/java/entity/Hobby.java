/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import dtos.HobbyDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author oscar
 */
@Entity
@Table(name = "Hobby")
@NamedQueries({
    @NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE FROM Hobby"),
    @NamedQuery(name = "Hobby.findByName", query = "SELECT h FROM Hobby h WHERE h.name LIKE :name"),
    @NamedQuery(name = "Hobby.getAll", query = "SELECT h FROM Hobby h ORDER BY h.name"),
})
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "hobbies")
    List<Person> persons = new ArrayList<>();

    public Hobby(){
        
    }
    
    public Hobby(String name,String description){
        this.name = name;
        this.description = description;
    }

    public Hobby(HobbyDTO hobbyDTO) {
        this.id=hobbyDTO.getId();
        this.name=hobbyDTO.getName();
        this.description=hobbyDTO.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void addPerson(Person person){
        if(!persons.contains(person)){
            persons.add(person);
    }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author oscar
 */
public class PersonFacade {

    private static EntityManagerFactory emf;
    private static PersonFacade instance;

    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public List<PersonDTO> getAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            List<Person> persons = entityManager.createNamedQuery("Person.getAll", Person.class).getResultList();
            return toPersonDTOList(persons);
        } finally {
            entityManager.close();
        }
    }

    public PersonDTO createPerson(PersonDTO personDTO){
        EntityManager entityManager = emf.createEntityManager();
        Person person = new Person(personDTO);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
        }catch(NullPointerException e){
            System.out.println("Woops! Person not created");
        }finally{
            entityManager.close();
        }
        return new PersonDTO(person);
                
    }
   
    public List<PersonDTO> getByPhone(String phone){
        EntityManager entityManager = emf.createEntityManager();
        try {
            List<Person> persons = entityManager.createNamedQuery("Person.getByPhone",  Person.class).setParameter("phone",phone).getResultList();
            if (persons.isEmpty()) {
                throw new NullPointerException ("No persons found!");
            }else{
                return toPersonDTOList(persons);
            }
        }finally{
            entityManager.close();
        }
    }

    public PersonDTO getById(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Person person = entityManager.find(Person.class,id);
            if (person == null) {
                throw new NullPointerException("No Persons found!");
            } else {
                return new PersonDTO(person);
            }
        } finally {
            entityManager.close();
        }
    }

     private List<PersonDTO> toPersonDTOList(List<Person> persons) {
        List<PersonDTO> dtos = new ArrayList<>();
        for (Person person : persons) {
            dtos.add(new PersonDTO(person));
        }
        return dtos;
    }
    
}

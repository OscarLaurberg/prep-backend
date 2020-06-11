/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author oscar
 */
public class PersonFacadeTest {
    private static EntityManagerFactory entityManagerFactory;
    private static PersonFacade personFacade;
    private static Person p1, p2;
    private static PersonDTO pd1, pd2;

    public PersonFacadeTest() {}
    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        personFacade = PersonFacade.getPersonFacade(entityManagerFactory);
        p1 = new Person("Peter", "Pan", "29803002","peterPan@gmail.com");
        p2 = new Person("Lars", "Larsen", "29803001","larsLarsen@gmail.com");

    }
    @BeforeEach
    public void setUp() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Person.deleteAllRows").executeUpdate();
            entityManager.persist(p1);
            entityManager.persist(p2);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        pd1 = new PersonDTO(p1);
        pd2 = new PersonDTO(p2);
    }
    
    @Test
    public void testGetaAll(){
        List<PersonDTO> dtos = personFacade.getAll();
         int expected = 2;
         System.out.println(dtos);
         int result = dtos.size();
         assertEquals(expected,result);
    }    

    @Test
    public void testGetPersonFromId(){
        String expectedFirstName = p2.getFirstName();
        PersonDTO person = personFacade.getById(p2.getId());
        String resultFirstName = person.getFirstName();
        assertEquals(expectedFirstName, resultFirstName);
    }
    
 
    @Test
    public void testGetPersonFromPhone(){
        int expected = 1;
        List <PersonDTO> pers = personFacade.getByPhone("29803002");
        int result = pers.size();
        assertEquals(expected, result);
        
    }
}

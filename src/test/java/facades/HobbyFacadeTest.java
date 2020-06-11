/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import entity.Hobby;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class HobbyFacadeTest {

    private static EntityManagerFactory entityManagerFactory;
    private static HobbyFacade hobbyFacade;
    private static Hobby h1, h2;
    private static HobbyDTO hd1, hd2;

    @BeforeAll
    public static void setUpClass() {
        entityManagerFactory = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        hobbyFacade = HobbyFacade.getHobbyFacade(entityManagerFactory);
        h1 = new Hobby("Running", "It's not funny at all");
        h2 = new Hobby("Eating", "Everybody does it");
    }

    @BeforeEach
    public void setUp() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            entityManager.persist(h1);
            entityManager.persist(h2);
            entityManager.getTransaction().commit();
        }finally{
            entityManager.close();
        }
        hd1 = new HobbyDTO(h1);
        hd2 = new HobbyDTO(h2);
    }
    
        @Test 
        public void testGetAll() {
        List<HobbyDTO> hobbies = hobbyFacade.getAll();
        String expected = "Eating";
        String result = hobbies.get(0).getName();
        assertEquals(expected,result);
    }
        @Test
        public void testGetAllAgain() {
        List<HobbyDTO> hobbies = hobbyFacade.getAll();
        int expected = 2;
        int result = hobbies.size();
        assertEquals(expected,result);
        }
}

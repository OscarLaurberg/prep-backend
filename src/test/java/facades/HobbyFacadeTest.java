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
        
        @Test
        public void testCreateHobby(){
            int expected = hobbyFacade.getAll().size()+1;            
            Hobby hobby = new Hobby("Swimming","It's tough");
            HobbyDTO hobbyDTO = new HobbyDTO(hobby);
            hobbyFacade.createHobby(hobbyDTO);
            int result = hobbyFacade.getAll().size();
            assertEquals(expected, result);
        }
        
        @Test
        public void testCreateHobbyAgain(){
        String expected = "Swimming";           
            Hobby hobby = new Hobby(expected,"It's tough");
            HobbyDTO hobbyDTO = new HobbyDTO(hobby);
            hobbyFacade.createHobby(hobbyDTO);
            String result = hobbyFacade.getAll().get(2).getName();
            assertEquals(expected, result);
        }
        
        @Test
        public void testUpdateHobbyDescription(){
            String expected = "My hand is a dolphin";
            HobbyDTO hobbyDTO = hobbyFacade.getAll().get(0);
            hobbyDTO.setDescription(expected);
            hobbyFacade.updateHobby(hobbyDTO);
            String result = hobbyFacade.getAll().get(0).getDescription();
            assertEquals(expected, result);
        }
        
        @Test
        public void testDeleteHobby(){
            int expected = hobbyFacade.getAll().size()-1;
            hobbyFacade.deleteHobby(h1.getId());
            int result = hobbyFacade.getAll().size();
            assertEquals(expected, result);
            
        }
}

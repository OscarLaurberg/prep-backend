/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import entity.Hobby;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jsoup.nodes.Entities;

/**
 *
 * @author oscar
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    private HobbyFacade() {

    }

    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    public List<HobbyDTO> getAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            List<Hobby> hobbies = entityManager.createNamedQuery("Hobby.getAll", Hobby.class)
                    .getResultList();
            return toHobbyDTOList(hobbies);
        } finally {
            entityManager.close();
        }
    }
    
    public HobbyDTO createHobby(HobbyDTO hobbyDTO){
       EntityManager entityManager = emf.createEntityManager();
       Hobby hobby = new Hobby(hobbyDTO);
       try{
           entityManager.getTransaction().begin();
           entityManager.persist(hobby);
           entityManager.getTransaction().commit();
       } catch (Exception e){
           throw new IllegalArgumentException("Hobby not created");
       }finally{
           entityManager.close();
       }
       return new HobbyDTO(hobby);
    }
    
    public HobbyDTO updateHobby(HobbyDTO hobbyDTO){
        EntityManager entityManager = emf.createEntityManager();
        try{
            Hobby hobby = entityManager.find(Hobby.class, hobbyDTO.getId());
            if (hobby == null){
                throw new IllegalArgumentException();
            }else{
                entityManager.getTransaction().begin();
                hobby.setDescription(hobbyDTO.getDescription());
                hobby.setName(hobbyDTO.getName());
                entityManager.merge(hobby);
                entityManager.getTransaction().commit();
                return hobbyDTO;
                       
            }
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Hobby not updated");
        }finally{
            entityManager.close();
        }        
    }
    
    public HobbyDTO deleteHobby(long id){
        EntityManager entityManager = emf.createEntityManager();
        try{
            Hobby hobby = entityManager.find(Hobby.class,id);
            if(hobby == null){
                throw new IllegalArgumentException();
            }else{
                entityManager.getTransaction().begin();
                entityManager.remove(hobby);
                entityManager.getTransaction().commit();
            }
            return new HobbyDTO(hobby);
        }finally{
            entityManager.close();
        }
            
    
    }
    
    

    private List<HobbyDTO> toHobbyDTOList(List<Hobby> hobbies) {
        List<HobbyDTO> dtos = new ArrayList<>();
        hobbies.forEach(hobby -> {
            dtos.add(new HobbyDTO(hobby));
        });
        return dtos;
    }

}

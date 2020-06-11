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

    private List<HobbyDTO> toHobbyDTOList(List<Hobby> hobbies) {
        List<HobbyDTO> dtos = new ArrayList<>();
        hobbies.forEach(hobby -> {
            dtos.add(new HobbyDTO(hobby));
        });
        return dtos;
    }

}

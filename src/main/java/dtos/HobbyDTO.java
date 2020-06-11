/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entity.Hobby;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oscar
 */
@Schema(name = "Hobby")
public class HobbyDTO {
    @Hidden
    private Long id;
    @Schema(required = true, example = "Chess")
    private String name;
    @Schema(example = "All we do is play chess")
    private String description;
    
    public HobbyDTO(){
        
    }
    
    public HobbyDTO (Long id, String name, String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }
    
       public HobbyDTO(Hobby hobby)  {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

           public static List<HobbyDTO> convertToHobbyDTO(List<Hobby> hobbies){
        List<HobbyDTO> hobbyDTOList = new ArrayList<>();
        for(Hobby hobby: hobbies) {
            hobbyDTOList.add(new HobbyDTO(hobby));
        }
        return hobbyDTOList;
    }
    
    public static List<Hobby> convertToHobby(List<HobbyDTO> hobbiesDTO){
        List<Hobby> hobbyList = new ArrayList<>();
        hobbiesDTO.forEach(hobbyDTO -> hobbyList.add(new Hobby(hobbyDTO)));
        return hobbyList;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entity.Person;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oscar
 */
@Schema(name = "Person")
public class PersonDTO {

    @Hidden
    private Long id;
    @Schema(required = true, example = "person@example.com")
    private String email;
    @Schema(required = true, example = "Frederik")
    private String firstName;
    @Schema(required = true, example = "Jensen")
    private String lastName;
    @Schema (required = true, example = "29803002")
    private String phone; 
    
    private List<HobbyDTO> hobbies = new ArrayList<>();
    private AddressDTO address;
    
    public PersonDTO(){
    }
    
    public PersonDTO (Long id, String email, String firstName, String lastName, String phone){
        this.id=id;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
    }
    
    public PersonDTO(Person person){
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone=person.getPhone();
        this.email=person.getEmail();
            
    }

    public PersonDTO(PersonDTO pers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

        public void addHobby(HobbyDTO h1) {
        hobbies.add(h1);
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "firstName=" + firstName + ", lastName=" + lastName + ", address=" + phone + '}';
    }
        
        

}

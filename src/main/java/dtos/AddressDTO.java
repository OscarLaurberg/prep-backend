/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author oscar
 */
@Schema(name = "Address")
public class AddressDTO {
    @Hidden
    private Long id;
    @Schema(required = true, example ="Jernbane Allé 7")
    private String street;
    @Schema(example = "Lyngby")
    private String city;
    @Schema (example = "2980")
    private int Zip;

    
    public AddressDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return Zip;
    }

    public void setZip(int Zip) {
        this.Zip = Zip;
    }



    
    
    
    
}
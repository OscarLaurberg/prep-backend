/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author oscar
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().create();


    @Operation(summary = "Get person information from id",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested person information"),
                @ApiResponse(responseCode = "404", description = "No person found with that id")})
    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO getPersonById(@PathParam("id") long id) {
        PersonDTO pers;
        try {
            pers = FACADE.getById(id);
            if (pers == null) {
                throw new NullPointerException("No person found with that ID");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("No person found with that ID");
        }
        return pers;
    }
    @Operation(summary = "Get person information from phone no",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                @ApiResponse(responseCode = "200", description = "The Requested person information"),
                @ApiResponse(responseCode = "404", description = "No person found with that phone no.")})
    @Path("/phone/{phoneNumber}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getPersonByPhone(@PathParam("phoneNumber") String phone){
       List<PersonDTO> persons;
        try {
            persons = FACADE.getByPhone(phone);
            if (persons == null){
                throw new NullPointerException("No Person found with that phone number");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("No Person found with that phone number");
        }
        return persons;
    }
    @Operation(summary = "Create a Person",
            tags = {"person"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                @ApiResponse(responseCode = "200", description = "The person is created"),
                @ApiResponse(responseCode = "404", description = "Person not created")})
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDTO createPersonFromDTO(PersonDTO pers) {
        if (pers.getFirstName() == null || pers.getLastName() == null || pers.getPhone() == null || pers.getEmail() == null) {
            throw new NullPointerException("Person not created!");
        }
        PersonDTO dto = new PersonDTO(pers);
        try {
            dto = FACADE.createPerson(dto);
        } catch (Exception e) {
            System.out.println("Person not created");
        }
        return dto;

    }

    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }

}

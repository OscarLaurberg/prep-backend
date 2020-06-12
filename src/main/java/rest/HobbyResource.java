/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import dtos.HobbyDTO;
import facades.HobbyFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@OpenAPIDefinition(
        info = @Info(
                title = "Hobby API",
                version = "0.1",
                description = "Simple API to get info about hobbies",
                contact = @Contact(name = "Oscar Laurberg", email = "cph-ol38@cphbusiness.dk")
        ),
        tags = {
            @Tag(name = "hobby", description = "API related to hobby info")

        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/prep/"
            ),
            @Server(
                    description = "Server API",
                    url = "not known yet"
            )

        }
)
@Path("hobby")
public class HobbyResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HobbyResource
     *
     *
     */
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final HobbyFacade FACADE = HobbyFacade.getHobbyFacade(EMF);

    public HobbyResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    /**
     * PUT method for updating or creating an instance of HobbyResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @Operation(summary = "Get all hobbies",
            tags = {"hobby"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                @ApiResponse(responseCode = "200", description = "All requested hobbies"),
                @ApiResponse(responseCode = "404", description = "Hobbies could not be fetched")})
    @Path("/all")
    @RolesAllowed({"admin"})
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<HobbyDTO> getAllHobbies() {
        List<HobbyDTO> hobbies;
        try {
            hobbies = FACADE.getAll();
            if (hobbies.isEmpty()) {
                throw new NullPointerException("No hobbies found!");

            }
        } catch (NullPointerException e) {
            throw new NullPointerException("No hobbies found!");
        }
        return hobbies;
    }

    @Operation(summary = "Create a hobby",
            tags = {"hobby"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                @ApiResponse(responseCode = "200", description = "The hobby is created"),
                @ApiResponse(responseCode = "404", description = "Hobby not created")})
    @POST
    @Path("/create")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public HobbyDTO createHobbyByDTO(HobbyDTO hobbyDTO) {
        if (hobbyDTO.getName() == null || hobbyDTO.getDescription() == null) {
            throw new IllegalArgumentException("Hobby not created");
        }
        HobbyDTO dto;
        try {
            dto = FACADE.createHobby(hobbyDTO);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Hobby not created");
        }
        return dto;

    }

    @Operation(summary = "Update a hobby",
            tags = {"hobby"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                @ApiResponse(responseCode = "200", description = "The hobby is updated"),
                @ApiResponse(responseCode = "404", description = "Hobby not updated")})
    @POST
    @Path("/update")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public HobbyDTO updateHobby(HobbyDTO hobbyDTO) {
        if (hobbyDTO.getName() == null || hobbyDTO.getDescription() == null) {
            throw new IllegalArgumentException("Hobby not updated");
        }
        HobbyDTO dto;
        try {
            dto = FACADE.updateHobby(hobbyDTO);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Hobby not updated");
        }
        return dto;
    }

    @Operation(summary = "Delete a hobby",
            tags = {"hobby"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                @ApiResponse(responseCode = "200", description = "The hobby is deleted"),
                @ApiResponse(responseCode = "404", description = "Hobby not deleted")})
    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public HobbyDTO deleteHobby(@PathParam("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Hobby not deleted");
        }
        HobbyDTO dto;
        try {
            dto = FACADE.deleteHobby(id);
       } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Hobby not deleted");
    }
        return dto;
    }
}

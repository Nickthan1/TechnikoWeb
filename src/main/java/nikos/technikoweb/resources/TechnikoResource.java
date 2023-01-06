/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import nikos.technikoweb.dto.PropertyOwnerDto;
import nikos.technikoweb.dto.PropertyRepairDto;
import nikos.technikoweb.services.RegisterService;

/**
 *
 * @author legeo
 */
@Path("/techniko")
public class TechnikoResource {
   
    @Inject
    private RegisterService registerService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public String home()  {
      return "TechnikoWeb";
    }
    
    @GET
    @Path("/propertyOwner")
    @RolesAllowed({"ADMIN","USER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PropertyOwnerDto> readAll() {
      return registerService.read();
    }
    
    @GET
    @Path("/propertyOwner/{propertyOwnerId}")
    @RolesAllowed({"ADMIN","USER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PropertyOwnerDto readOne(@PathParam("propertyOwnerId") int propertyOwnerId) {
     return registerService.read(propertyOwnerId);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/propertyOwner")
    @RolesAllowed({"ADMIN","USER"})
    public PropertyOwnerDto insert(PropertyOwnerDto propertyOwner) {
     return registerService.create(propertyOwner);
    }
    @POST
    @Path("/propertyRepair/{propertyOwnerId}")
    @RolesAllowed({"ADMIN","USER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PropertyRepairDto createPropertyRepair(@jakarta.ws.rs.PathParam("propertyOwnerId") Long propertyOwnerId) {
        return registerService.createPropertyRepair(propertyOwnerId);
    }
    
}

package it.itsincom.webdevd.web;

import it.itsincom.webdevd.persistence.model.ApplicationUser;
import it.itsincom.webdevd.service.UserService;
import it.itsincom.webdevd.web.model.CreateUserRequest;
import it.itsincom.webdevd.web.model.UpdateUserRequest;
import it.itsincom.webdevd.web.model.UserResponse;
import jakarta.transaction.UserTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;



import java.util.List;
import java.util.Set;

@Path("/user")
public class UserResource {

    private final UserService userService;
    private final JsonWebToken jwtWebToken;
    private UserTransaction userTransaction;

    public UserResource(UserService userService, JsonWebToken jwtWebToken) {
        this.userService = userService;
        this.jwtWebToken = jwtWebToken;
    }

    @jakarta.inject.Inject
    public UserResource(UserService userService, JsonWebToken jwtWebToken, UserTransaction userTransaction) {
        this.userService = userService;
        this.jwtWebToken = jwtWebToken;
        this.userTransaction = userTransaction;
    }


    @POST
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse register(CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PUT
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyUser(UpdateUserRequest user, @PathParam("id") int id) {
        Set<String> groups = jwtWebToken.getGroups();

        if (groups.contains("USER") && id != Integer.parseInt(jwtWebToken.getSubject())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean response = userService.update(user, id);

        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserResponse> findAll() {
        return userService.findAll();
    }
}

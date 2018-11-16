import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/register")
public class Registration {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void registerUser(@FormParam("username") String username,
                             @FormParam("password") String password) {

        User user = new User(username, password);
    }
}

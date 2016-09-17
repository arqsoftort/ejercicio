package ejercicio;

import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("usuario")
public class UsuarioResource {
    
    @EJB
    private UsuarioBean usuarioBean;
    
    @EJB
    private MonedaBean monedaBean;

    @Context
    private UriInfo context;

    public UsuarioResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        List<UsuarioEntity> list = usuarioBean.listar();
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(String body) {
        Gson gson = new Gson();
        UsuarioEntity u = gson.fromJson(body, UsuarioEntity.class);
        Response r;
        UsuarioEntity creado = usuarioBean.agregar(u);
        if (creado == null) {
            r = Response
                    .status(Status.BAD_REQUEST)
                    .entity("afadfadf")
                    .build();
        } else {
            r = Response
                    .status(Status.CREATED)
                    .entity(gson.toJson(creado))
                    .build();
        }
        return r;
    } 
    
}

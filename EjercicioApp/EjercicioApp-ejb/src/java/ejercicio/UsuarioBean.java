package ejercicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UsuarioBean {

    private static final String ADMIN = "ADMIN";
    
    @EJB
    private MonedaBean monedaBean;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    private void init() {
        System.out.println("INSTANCIA USUARIO BEAN");
    }
    
    public UsuarioEntity agregar(UsuarioEntity u) {
        return agregar(u.getNombre(), u.getSalario());
    }
    
    public UsuarioEntity agregar(String nombre, float salarioPesos) {
        
        UsuarioEntity u = new UsuarioEntity();
        u.setNombre(nombre);
        Float salarioDol = (float) salarioPesos / (float)  monedaBean.getDolares();
        u.setSalario(salarioDol);
        u.setFechaNac(new Date());
        
        em.persist(u);
        
        return u;
    }
    
    public UsuarioEntity modificar(Long id, String nombreNuevo) {
        
        UsuarioEntity u = em.find(UsuarioEntity.class, id);
        
        u.setNombre(nombreNuevo);
        
        em.merge(u);
        
        return u;
    }
    
    public boolean eliminar(Long id) {
        UsuarioEntity u = em.find(UsuarioEntity.class, id);
        
        em.remove(u);
        
        return true;
    }
    
    public List<UsuarioEntity> listar() {
        
        List<UsuarioEntity> list = 
                em
                    .createQuery("select u from Usuario u")
                    .getResultList();
        
        return list;
    }
    
    public Usuario buscar(Long id) {
        UsuarioEntity ent = em.find(UsuarioEntity.class, id);
        Usuario u = new Usuario();
        u.setId(ent.getId());
        u.setNombre(ent.getNombre());
        return u;
    }
    
    public List<UsuarioEntity> buscar(String nombre) {
        List<UsuarioEntity> list = 
                em
                    .createQuery("select u from Usuario u "
                            + "where u.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
        
        return list;
    }
    
}

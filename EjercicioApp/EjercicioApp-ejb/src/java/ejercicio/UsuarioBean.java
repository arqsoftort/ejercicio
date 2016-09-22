package ejercicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UsuarioBean {

    private static final String ADMIN = "ADMIN";
    
    @Resource(lookup = "jms/ConnectionFactory")
    private ConnectionFactory cf;
    
    @Resource(lookup = "jms/Queue")
    private Queue queue;
    
    @Resource(lookup = "jms/Topic")
    private Topic topic;
    
    @EJB
    private MonedaBean monedaBean;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    private void init() {
        System.out.println("INSTANCIA USUARIO BEAN");
    }
    
    private void encolar(String texto) {
        try {
            Connection connection = cf.createConnection();
            Session session = connection.createSession();
            TextMessage msg = session.createTextMessage(texto);
            MessageProducer producer = session.createProducer(topic);
            producer.send(msg);
            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public UsuarioEntity agregar(UsuarioEntity u) {
        return agregar(u.getNombre(), u.getSalario());
    }
    
    public UsuarioEntity agregar(String nombre, Float salarioPesos) {
        
        System.out.println("EMPIEZA USUARIO BEAN");

        UsuarioEntity u = new UsuarioEntity();
        u.setNombre(nombre);
        Float salarioDol = (float) salarioPesos / (float)  monedaBean.getDolares();
        u.setSalario(salarioDol);
        u.setFechaNac(new Date());
        
        em.persist(u);
        
        encolar("Se creo el usuario " + u.getNombre());
        
        System.out.println("TERMINO USUARIO BEAN");
        
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
                    .createQuery("select u from UsuarioEntity u")
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
                    .createQuery("select u from UsuarioEntity u "
                            + "where u.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
        
        return list;
    }
    
}

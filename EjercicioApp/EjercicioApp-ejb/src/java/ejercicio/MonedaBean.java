package ejercicio;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class MonedaBean {

    private int dolares = 20;
    private int euros = 30;
    
    @PostConstruct
    private void init() {
        System.out.println("INSTANCIA MONEDA BEAN");
    }
    
    public int getDolares() {
        return dolares;
    }
    
    public int getEuros() {
        return euros;
    }
    
    public void cambiarCotizacion(int dolares, int euros) {
        this.dolares = dolares;
        this.euros = euros;
    }
    
}

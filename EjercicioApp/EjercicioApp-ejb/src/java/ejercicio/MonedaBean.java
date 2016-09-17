package ejercicio;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class MonedaBean {

    private int contadorLlamadas;
    
    private int dolares = 20;
    private int euros = 30;
    
    @PostConstruct
    private void init() {
        System.out.println("INSTANCIA MONEDA BEAN");
    }
    
    public int getDolares() {
        contadorLlamadas++;
        return dolares;
    }
    
    public int getEuros() {
        contadorLlamadas++;
        return euros;
    }
    
    public void cambiarCotizacion(int dolares, int euros) {
        contadorLlamadas++;
        this.dolares = dolares;
        this.euros = euros;
    }
    
    public int getLlamadas() {
        return ++contadorLlamadas;
    }
    
}

package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 11:29
 **/

@Entity
@Table(name = "solucion")
public class Solucion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idsolucion;

    private String enunciadoPre;

    private String respuestaPre;

    private String respuestaEst;

    @ManyToOne
    @JoinColumn(name = "idrespuesta")
    private Respuesta respuesta;

    /* Getter y Setter */

    public int getIdsolucion() {
        return idsolucion;
    }

    public void setIdsolucion(int idsolucion) {
        this.idsolucion = idsolucion;
    }

    public String getEnunciadoPre() {
        return enunciadoPre;
    }

    public void setEnunciadoPre(String enunciadoPre) {
        this.enunciadoPre = enunciadoPre;
    }

    public String getRespuestaPre() {
        return respuestaPre;
    }

    public void setRespuestaPre(String respuestaPre) {
        this.respuestaPre = respuestaPre;
    }

    public String getRespuestaEst() {
        return respuestaEst;
    }

    public void setRespuestaEst(String respuestaEst) {
        this.respuestaEst = respuestaEst;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
}

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
    private Respuesta idrespuesta;
}

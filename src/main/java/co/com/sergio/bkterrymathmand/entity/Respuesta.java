package co.com.sergio.bkterrymathmand.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 11:26
 **/

@Entity
@Table(name = "respuesta")
public class Respuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrespuesta;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "respuesta")
    private List<Solucion> soluciones;

    private int acertadas;

    private float nota;

    @DateTimeFormat(pattern = "%Y-%m-%d")
    private Date fecha;

    /* Getter y Setter */

    public int getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(int idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public List<Solucion> getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(List<Solucion> soluciones) {
        this.soluciones = soluciones;
    }

    public int getAcertadas() {
        return acertadas;
    }

    public void setAcertadas(int acertadas) {
        this.acertadas = acertadas;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

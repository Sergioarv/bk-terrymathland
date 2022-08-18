package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 12/06/2022 15:08
 **/

@Entity
@Table(name="cartilla")
public class Cartilla implements Serializable {
    @Id
    @Column(name = "idcartilla", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcartilla;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "cartillas")
    private List<Pregunta> preguntas;

    /** Getter y Setter **/

    public int getIdcartilla() {
        return idcartilla;
    }

    public void setIdcartilla(int idcartilla) {
        this.idcartilla = idcartilla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}

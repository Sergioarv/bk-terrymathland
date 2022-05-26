package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:41
 **/

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Usuario implements Serializable {

    @Id
    @Column(name = "idusuario")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int idusuario;

    @Column(nullable = false)
    private String nombre;

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:41
 **/

@Inheritance
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;

    @Column(nullable = false)
    private String nombre;

    public Usuario(int idusuario, String nombre) {
        this.idusuario = idusuario;
        this.nombre = nombre;
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario() {
    }

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

package co.com.sergio.bkterrymathmand.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:41
 **/



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario implements Serializable {

    @Id
    @Column(name = "idusuario")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int idusuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String documento;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "idusuario", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "idrol", nullable = false))
    private Set<Rol> roles = new HashSet<>();


    /** Constructor **/
    public Usuario() {
    }

    public Usuario(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    /** Getter y Setter **/

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

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
}

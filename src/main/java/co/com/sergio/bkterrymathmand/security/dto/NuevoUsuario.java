package co.com.sergio.bkterrymathmand.security.dto;
import co.com.sergio.bkterrymathmand.entity.Rol;
import java.util.HashSet;
import java.util.Set;

public class NuevoUsuario {

    private String nombre;

    private String documento;

    private Set<String> roles = new HashSet<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

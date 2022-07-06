package co.com.sergio.bkterrymathmand.security.entity;

import co.com.sergio.bkterrymathmand.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {

    private String nombre;

    private String documento;

    private Collection<? extends GrantedAuthority> autoridades;

    public UsuarioPrincipal(String nombre, String documento, Collection<? extends GrantedAuthority> autoridades) {
        this.nombre = nombre;
        this.documento = documento;
        this.autoridades = autoridades;
    }

    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> autoridades = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(
                rol.getRolNombre().name())).collect(Collectors.toList());

        return new UsuarioPrincipal(usuario.getNombre(), usuario.getDocumento(), autoridades);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public String getPassword() {
        return documento;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

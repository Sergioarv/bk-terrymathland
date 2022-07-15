package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.repository.UsuarioRepository;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolServiceImpl rolService;

    @Override
    public Usuario obtenerUsuarioPorDocumento(String documento) throws Exception {
        Optional<Usuario> u = usuarioRepository.findByDocumento(documento);
        if(u.isPresent()){
            return u.get();
        }
        throw new Exception("El usuario no existe");
    }

    @Override
    public Usuario agregarAdministrador(Usuario admin) {

        if (usuarioRepository.existePorDocumento(admin.getDocumento()) == null) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            admin.setRoles(roles);

            return usuarioRepository.save(admin);
        }
        return null;
    }
}

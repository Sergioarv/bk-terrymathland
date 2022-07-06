package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario obtenerUsuarioPorNombre(String username) throws Exception {
        Optional<Usuario> u = usuarioRepository.findByNombre(username);
        if(u.isPresent()){
            return u.get();
        }
        throw new Exception("El usuario no existe");
    }
}

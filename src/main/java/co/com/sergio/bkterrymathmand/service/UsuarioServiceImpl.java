package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario obtenerUsuarioPorNombre(String username) {
        return usuarioRepository.obtenerUsuarioPorNombre(username);
    }
}

package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Usuario;

public interface UsuarioService {

    Usuario obtenerUsuarioPorNombre(String username) throws Exception;

    Usuario agregarAdministrador(Usuario admin);
}

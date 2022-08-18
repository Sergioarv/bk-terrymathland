package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Usuario;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:41
 **/
public interface UsuarioService {

    Usuario obtenerUsuarioPorDocumento(String username) throws Exception;

    Usuario agregarAdministrador(Usuario admin);
}

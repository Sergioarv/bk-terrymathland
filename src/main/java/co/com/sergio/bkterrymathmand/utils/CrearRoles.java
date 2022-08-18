package co.com.sergio.bkterrymathmand.utils;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import co.com.sergio.bkterrymathmand.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @project bk-terry-math-land
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 14/07/2022 16:51
 **/
@Component
public class CrearRoles implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Rol admin;
        Rol docente;
        Rol estudiante;

        if (!rolService.getByRolNombre(RolNombre.ROLE_ADMIN).isPresent()) {
            admin = new Rol(RolNombre.ROLE_ADMIN);
            rolService.crearRol(admin);
        }

        if (!rolService.getByRolNombre(RolNombre.ROLE_DOCENTE).isPresent()) {
            docente = new Rol(RolNombre.ROLE_DOCENTE);
            rolService.crearRol(docente);
        }

        if (!rolService.getByRolNombre(RolNombre.ROLE_ESTUDIANTE).isPresent()) {
            estudiante = new Rol(RolNombre.ROLE_ESTUDIANTE);
            rolService.crearRol(estudiante);
        }
    }
}

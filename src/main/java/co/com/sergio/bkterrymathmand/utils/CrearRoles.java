package co.com.sergio.bkterrymathmand.utils;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import co.com.sergio.bkterrymathmand.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

        if (!rolService.getByRolNombre(RolNombre.ROLE_ADMIN).isPresent()) {
            Rol r1 = new Rol(RolNombre.ROLE_ADMIN);
            Rol r2 = new Rol(RolNombre.ROLE_DOCENTE);
            Rol r3 = new Rol(RolNombre.ROLE_ESTUDIANTE);

            rolService.crearRol(r1);
            rolService.crearRol(r2);
            rolService.crearRol(r3);
        }
    }
}

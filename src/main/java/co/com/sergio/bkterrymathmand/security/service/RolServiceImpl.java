package co.com.sergio.bkterrymathmand.security.service;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.security.Repository.RolRepository;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolServiceImpl {

    @Autowired
    private RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return  rolRepository.findByRolNombre(rolNombre);
    }

    public void crearRol(Rol rol) {
        rolRepository.save(rol);
    }
}

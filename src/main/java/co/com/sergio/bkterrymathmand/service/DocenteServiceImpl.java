package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.repository.DocenteRepository;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 11:01
 **/

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    RolServiceImpl rolService;

    @Override
    public List<Docente> obtenerDocentes() {
        return docenteRepository.findAll();
    }

    @Override
    public Docente docentePorNombre(String nombre) {
        return docenteRepository.docentePorNombre(nombre);
    }

    @Override
    public Docente agregarDocente(Docente docente) {
        if (docenteRepository.existePorDocumento(docente.getDocumento()) == null) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROL_DOCENTE).get());
            docente.setRoles(roles);

            return docenteRepository.save(docente);
        }
        return null;
    }
}

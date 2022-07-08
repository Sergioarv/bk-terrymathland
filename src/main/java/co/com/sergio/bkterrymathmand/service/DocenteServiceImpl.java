package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.repository.DocenteRepository;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<Docente> obtenerDocentes() {
        return docenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Docente docentePorNombre(String nombre) {
        return docenteRepository.docentePorNombre(nombre);
    }

    @Override
    @Transactional
    public Docente agregarDocente(Docente docente) {
        if (docenteRepository.existePorDocumento(docente.getDocumento()) == null) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_DOCENTE).get());
            docente.setRoles(roles);

            return docenteRepository.save(docente);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> filtrarEstudiante(String nombre, String correo) {
        if (nombre != null && correo != null) {
            return docenteRepository.estudiantePorNombreYCorreo(nombre);
        } else if (nombre != null) {
            return docenteRepository.estudiantePorFiltro(nombre);
        } else if (correo != null) {
            return docenteRepository.estudiantePorCorreo(correo);
        } else {
            return docenteRepository.findAll(Sort.by(Sort.Direction.ASC, "idusuario"));
        }
    }

    @Override
    @Transactional
    public Docente editarDocente(Docente docente) {
        List<Docente> result = docenteRepository.getAllDocumento(docente.getDocumento());
        if (result.size() == 1) {
            if(result.get(0).getIdusuario() == docente.getIdusuario()){
                return docenteRepository.save(docente);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean eliminarDocente(Docente docente) {
        docenteRepository.delete(docente);
        return true;
    }
}

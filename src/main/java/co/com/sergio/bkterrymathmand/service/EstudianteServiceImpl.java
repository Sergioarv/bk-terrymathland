package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:40
 **/

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    RolServiceImpl rolService;


    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    @Transactional
    public Estudiante agregarEstudiante(Estudiante estudiante) {

        if (estudianteRepository.existePorDocumento(estudiante.getDocumento()) == null) {
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ESTUDIANTE).get());
            estudiante.setRoles(roles);

            return estudianteRepository.save(estudiante);
        }
        return null;
    }

    @Override
    @Transactional
    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public boolean eliminarEstudiante(Estudiante estudiante) {
        estudianteRepository.delete(estudiante);
        return true;
    }

    @Override
    public Estudiante obtenerEstudiantePorNombre(String nombre) {
        return estudianteRepository.obtenerEstudiantePorNombre(nombre);
    }

    @Override
    public List<IEstudianteProyeccion> obtenerIdyNombreEstudiantes() {
        return estudianteRepository.obtenerIdyNombreEstudiantes();
    }

    @Override
    @Transactional(readOnly = true)
    public IEstudianteProyeccion estudianteByNombre(String nombre) {
        return estudianteRepository.estudianteByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> filtrarEstudiante(String nombre, Date fecha) {

        if (nombre != null && fecha != null) {
            return estudianteRepository.estudiantePorNombreYFecha(nombre, fecha);
        } else if (nombre != null) {
            return estudianteRepository.estudiantePorFiltro(nombre);
        } else if (fecha != null) {
            return estudianteRepository.estudiantePorFechaRespuesta(fecha);
        } else {
            return estudianteRepository.findAll(Sort.by(Sort.Direction.ASC, "idusuario"));
        }
    }
}

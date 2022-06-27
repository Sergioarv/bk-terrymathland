package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante agregarEstudiante(Estudiante estudiante){

        IEstudianteProyeccion result = estudianteRepository.estudianteByNombre(estudiante.getNombre());

        if(result == null){
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
    public Page<Estudiante> filtrarEstudiante(String nombre, Date fecha, PageRequest pageable) {

        if(nombre != null && fecha != null){
            return estudianteRepository.estudiantePorNombreYFecha(nombre, fecha, pageable);
        } else if( nombre != null ){
            return estudianteRepository.estudiantePorFiltro(nombre, pageable);
        } else if (fecha != null){
            return estudianteRepository.estudiantePorFechaRespuesta(fecha, pageable);
        } else{
            return estudianteRepository.findAll(pageable);
        }
    }
}

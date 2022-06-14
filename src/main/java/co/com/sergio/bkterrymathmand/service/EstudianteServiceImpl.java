package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional(readOnly = true)
    public IEstudianteProyeccion estudianteByNombre(String nombre) {
        return estudianteRepository.estudianteByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> filtrarEstudiante(String nombre, Date fecha) {

        if(nombre != null && fecha != null){
            return estudianteRepository.estudiantePorNombreYFecha(nombre, fecha);
        } else if( nombre != null ){
            return estudianteRepository.estudiantePorFiltro(nombre);
        } else if (fecha != null){
            return estudianteRepository.estudiantePorFechaRespuesta(fecha);
        } else{
            return estudianteRepository.findAll(Sort.by(Sort.Direction.ASC, "idusuario"));
        }
    }
}

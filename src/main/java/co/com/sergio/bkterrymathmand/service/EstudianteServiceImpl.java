package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private RespuestaRepository respuestaRepository;

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private PreguntaService preguntaService;

    @DateTimeFormat(pattern = "%Y-%m-%d")
    Date fechaActual;

    @Override
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante estudianteByNombre(String nombre) {
        return estudianteRepository.estudianteByNombre(nombre);
    }

    @Override
    public Estudiante saveEstudiante(Estudiante estudiante) {

        LocalDate hoy = LocalDate.now();
        fechaActual = java.sql.Date.valueOf(hoy);

        Respuesta data = respuestaRepository.findRespuestaByFechaAndUsuario(fechaActual, estudiante.getIdusuario());

        estudiante.getRespuestas().get(0).setFecha(fechaActual);

        if(data != null){

            if(estudiante.getRespuestas().get(0).getNota() >= data.getNota()) {

                for (int i = 0; i < data.getSoluciones().size(); i++) {
                    estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
                    estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
                }

                estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));

                estudiante.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
                estudiante.getRespuestas().get(0).setUsuario(estudiante);

                respuestaRepository.save(estudiante.getRespuestas().get(0));
            }
        }else{
            estudiante.getRespuestas().get(0).setIdrespuesta(0);
            estudiante.getRespuestas().get(0).setUsuario(estudiante);

            Respuesta res = respuestaRepository.save(estudiante.getRespuestas().get(0));
            List<Pregunta> pre = preguntaService.findAllPregunta();

            for (int i = 0; i < pre.size(); i++) {
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(0);
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(res);
            }

            estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));
        }

        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> filtrarEstudiante(String nombre, Date fecha) {

        List<Estudiante> listResult;

        if(nombre != null && fecha != null){
            listResult = estudianteRepository.estudiantePorNombreYFecha(nombre, fecha);
        } else if( nombre != null ){
            listResult = estudianteRepository.estudiantePorFiltro(nombre);
        } else if (fecha != null){
            listResult = estudianteRepository.estudiantePorFechaRespuesta(fecha);
        } else{
            listResult = estudianteRepository.findAll();
        }
        return listResult;
    }
}

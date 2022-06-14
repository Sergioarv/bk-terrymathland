package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:30
 **/

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @DateTimeFormat(pattern = "%Y-%m-%d")
    Date fechaActual;

    @Override
    public List<Respuesta> obtenerRespuestas() {
        return respuestaRepository.findAll();
    }

    @Override
    public List<Respuesta> obtenerRespuestaPorFecha(Date fecha) {
        return respuestaRepository.obtenerRespuestasPorFecha(fecha);
    }

    @Override
    public Respuesta obtenerRespuestaPorFechaYEstudiante(Date fecha, int idusuario) {
        return respuestaRepository.obtenerRespuestaPorFechaYidUsuario(fecha, idusuario);
    }

    @Override
    public Respuesta saveRespuesta(Respuesta respuesta) {

        Respuesta data = null;

        if (data != null) {
            respuesta.setIdrespuesta(data.getIdrespuesta());
        }

        respuestaRepository.save(respuesta);

        return null;
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorFiltro(Estudiante estudiante, Date fecha) {

        if (estudiante != null && fecha != null) {
            return respuestaRepository.respuestasPorEstudianteYFecha(Integer.valueOf(estudiante.getIdusuario()), fecha);
        } else if (estudiante != null) {
            return respuestaRepository.respuestaPorEstudiante(Integer.valueOf(estudiante.getIdusuario()));
        } else if (fecha != null) {
            return respuestaRepository.obtenerRespuestasPorFecha(fecha);
        } else {
            return respuestaRepository.findAll(Sort.by(Sort.Direction.ASC, "fecha"));
        }
    }

    /**
     * Método encargado de guardar la respuesta enviada por el minijuego
     *
     * @param estudiante, estudiante con la respuesta y la lista de soluciones enviada por el minijuego
     * @return
     */
    @Transactional
    public List<IRespuestaProyeccion> guardarRespuestaEstudiante(Estudiante estudiante) {

        LocalDate hoy = LocalDate.now();
        fechaActual = java.sql.Date.valueOf(hoy);

        Respuesta data = respuestaRepository.obtenerRespuestaPorFechaYidUsuario(fechaActual, estudiante.getIdusuario());

        estudiante.getRespuestas().get(0).setFecha(fechaActual);

        int tamSe = estudiante.getRespuestas().get(0).getSoluciones().size();
        int tamSd = data.getSoluciones().size();

        if (data != null) {
            if (estudiante.getRespuestas().get(0).getNota() >= data.getNota()) {
                if (tamSe < tamSd) {
                    guardarMenosSoluciones(tamSe, tamSd, estudiante, data);
                } else if (tamSe > tamSd) {
                    guardarMasSoluciones(tamSe, tamSd, estudiante, data);
                } else {
                    guardarMismaSoluciones(tamSd, estudiante, data);
                }
            }
        } else {
            estudiante.getRespuestas().get(0).setIdrespuesta(0);
            estudiante.getRespuestas().get(0).setUsuario(estudiante);

            Respuesta res = respuestaRepository.save(estudiante.getRespuestas().get(0));

            for (int i = 0; i < tamSe; i++) {
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(0);
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(res);
            }

            estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));
            estudianteRepository.save(estudiante);
        }
        return respuestaRepository.respuestaGuardadaPorEstudiante(estudiante.getIdusuario());
    }

    private void guardarMismaSoluciones(int tamSd, Estudiante estudiante, Respuesta data) {

        for (int i = 0; i < tamSd; i++) {
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
        }

        estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));

        estudiante.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
        estudiante.getRespuestas().get(0).setUsuario(estudiante);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }

    private void guardarMasSoluciones(int tamSe, int tamSd, Estudiante estudiante, Respuesta data) {

        for (int i = 0; i < tamSd; i++) {
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
        }

        for (int j = tamSd; j < tamSe; j++) {
            estudiante.getRespuestas().get(0).getSoluciones().get(j).setIdsolucion(0);
            estudiante.getRespuestas().get(0).getSoluciones().get(j).setRespuesta(data);
        }

        estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));

        estudiante.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
        estudiante.getRespuestas().get(0).setUsuario(estudiante);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }

    private void guardarMenosSoluciones(int tamSe, int tamSd, Estudiante estudiante, Respuesta data) {

        List<Solucion> sobrantes = new ArrayList<>();

        for (int i = 0; i < tamSe; i++) {
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
        }

        for (int j = tamSe; j < tamSd; j++) {
            sobrantes.add(data.getSoluciones().get(j));
        }

        solucionRepository.deleteAll(sobrantes);

        estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));

        estudiante.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
        estudiante.getRespuestas().get(0).setUsuario(estudiante);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }
}

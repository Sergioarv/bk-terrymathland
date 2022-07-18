package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IDatosaGraficarDTO;
import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.repository.EstudianteRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
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

//    @DateTimeFormat(pattern = "%Y-%m-%d")
//    Date fechaActual;
//
//    @DateTimeFormat(pattern = "%Y-%m-%d")
//    Date fechaHoy;

    @Override
    @Transactional(readOnly = true)
    public List<Respuesta> obtenerRespuestas() {
        return respuestaRepository.findAll();
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<Respuesta> obtenerRespuestaPorFecha(Date fecha) {
//        return respuestaRepository.obtenerRespuestasPorFecha(fecha, pageable);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public Respuesta obtenerRespuestaPorFechaYEstudiante(Date fecha, int idusuario) {
//        return respuestaRepository.obtenerRespuestaPorFechaYidUsuario(fecha, idusuario);
//    }

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
    @Transactional(readOnly = true)
    public Page<Respuesta> obtenerRespuestasPorFiltro(Estudiante estudiante, Date fecha, Pageable pageable) {

        if (estudiante != null && fecha != null) {
            return respuestaRepository.respuestasPorEstudianteYFecha(estudiante.getIdusuario(), fecha, pageable);
        } else if (estudiante != null) {
            return respuestaRepository.respuestaPorEstudiante(estudiante.getIdusuario(), pageable);
        } else if (fecha != null) {
            return respuestaRepository.obtenerRespuestasPorFecha(fecha, pageable);
        } else {
            return respuestaRepository.findAll(pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IDatosaGraficarDTO graficarRespuestas(Estudiante estudiante, LocalDate fecha) {

        IDatosaGraficarDTO datosaGraficarDTO = new IDatosaGraficarDTO();

        if (estudiante != null && fecha != null) {
            datosaGraficarDTO.setListaPromedioNotas(respuestaRepository.graficarRespuestasNotasPorIdFecha(estudiante.getIdusuario(), fecha));
            datosaGraficarDTO.setListaPromedioEstudiantes(respuestaRepository.graficarRespuestasEstudiantesPorIdFecha(estudiante.getIdusuario(), fecha));
        } else if (estudiante != null) {
            datosaGraficarDTO.setListaPromedioNotas(respuestaRepository.graficarRespuestasNotasPorId(estudiante.getIdusuario()));
            datosaGraficarDTO.setListaPromedioEstudiantes(respuestaRepository.graficarRespuestasEstudiantesPoId(estudiante.getIdusuario()));
        } else if (fecha != null) {
            datosaGraficarDTO.setListaPromedioNotas(respuestaRepository.graficarRespuestasNotas(fecha));
            datosaGraficarDTO.setListaPromedioEstudiantes(respuestaRepository.graficarRespuestasEstudiantesPorFecha(fecha));
        } else {
//            LocalDate hoy = LocalDate.now();
//            fechaHoy = java.sql.Date.valueOf(hoy);
            LocalDate fechaHoy = LocalDate.now(ZoneId.of("America/Bogota"));
            datosaGraficarDTO.setListaPromedioNotas(respuestaRepository.graficarRespuestasNotas(fechaHoy));
            datosaGraficarDTO.setListaPromedioEstudiantes(respuestaRepository.graficarRespuestasEstudiantes());
        }
        return datosaGraficarDTO;
    }

    /**
     * Método encargado de guardar la respuesta enviada por el minijuego
     *
     * @param estudiante, estudiante con la respuesta y la lista de soluciones enviada por el minijuego
     * @return
     */
    @Override
    @Transactional
    public List<IRespuestaProyeccion> guardarRespuestaEstudiante(Estudiante estudiante) {

        LocalDate hoy = LocalDate.now(ZoneId.of("America/Bogota"));
//        LocalDate hoy = LocalDate.now();
//        ZonedDateTime zdt = hoy.atStartOfDay(ZoneId.of("America/Bogota"));

        Respuesta data = respuestaRepository.obtenerRespuestaPorFechaYidUsuario(hoy, estudiante.getIdusuario());
        Estudiante estudianteGuardado = estudianteRepository.getById(estudiante.getIdusuario());

        estudiante.getRespuestas().get(0).setFecha(hoy);

        int tamSe = estudiante.getRespuestas().get(0).getSoluciones().size();

        if (data != null) {

            int tamSd = data.getSoluciones().size();

            if (estudiante.getRespuestas().get(0).getNota() >= data.getNota()) {
                if (tamSe < tamSd) {
                    guardarMenosSoluciones(tamSe, tamSd, estudiante, data, estudianteGuardado);
                } else if (tamSe > tamSd) {
                    guardarMasSoluciones(tamSe, tamSd, estudiante, data, estudianteGuardado);
                } else {
                    guardarMismaSoluciones(tamSd, estudiante, data, estudianteGuardado);
                }
            }
        } else {
            estudiante.getRespuestas().get(0).setIdrespuesta(0);
            estudiante.getRespuestas().get(0).setUsuario(estudianteGuardado);

            Respuesta res = respuestaRepository.save(estudiante.getRespuestas().get(0));

            for (int i = 0; i < tamSe; i++) {
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(0);
                estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(res);
            }

            estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));
            estudiante.getRespuestas().get(0).setCantidadPreguntas(estudiante.getRespuestas().get(0).getSoluciones().size());
            estudiante.getRespuestas().get(0).setIntentos(1);
            respuestaRepository.save(estudiante.getRespuestas().get(0));
        }
        return respuestaRepository.respuestaGuardadaPorEstudiante(estudianteGuardado.getIdusuario());
    }


    private void guardarMismaSoluciones(int tamSd, Estudiante estudiante, Respuesta data, Estudiante estudianteGuardado) {

        for (int i = 0; i < tamSd; i++) {
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
            estudiante.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
        }

        estudiante.getRespuestas().get(0).setSoluciones(solucionRepository.saveAll(estudiante.getRespuestas().get(0).getSoluciones()));
        estudiante.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
        estudiante.getRespuestas().get(0).setUsuario(estudianteGuardado);
        estudiante.getRespuestas().get(0).setCantidadPreguntas(estudiante.getRespuestas().get(0).getSoluciones().size());
        estudiante.getRespuestas().get(0).setIntentos(data.getIntentos() + 1);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }

    private void guardarMasSoluciones(int tamSe, int tamSd, Estudiante estudiante, Respuesta data, Estudiante estudianteGuardado) {

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
        estudiante.getRespuestas().get(0).setUsuario(estudianteGuardado);
        estudiante.getRespuestas().get(0).setCantidadPreguntas(estudiante.getRespuestas().get(0).getSoluciones().size());
        estudiante.getRespuestas().get(0).setIntentos(data.getIntentos() + 1);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }

    private void guardarMenosSoluciones(int tamSe, int tamSd, Estudiante estudiante, Respuesta data, Estudiante estudianteGuardado) {

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
        estudiante.getRespuestas().get(0).setUsuario(estudianteGuardado);
        estudiante.getRespuestas().get(0).setCantidadPreguntas(estudiante.getRespuestas().get(0).getSoluciones().size());
        estudiante.getRespuestas().get(0).setIntentos(data.getIntentos() + 1);

        respuestaRepository.save(estudiante.getRespuestas().get(0));
    }
}

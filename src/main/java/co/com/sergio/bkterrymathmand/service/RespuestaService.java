package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IDatosaGraficarDTO;
import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:18
 **/

public interface RespuestaService {

    List<Respuesta> obtenerRespuestas();

//    List<Respuesta> obtenerRespuestaPorFecha(Date fecha);

    Respuesta obtenerRespuestaPorFechaYEstudiante(Date fecha, int idusuario);

    Respuesta saveRespuesta(Respuesta respuesta);

    Page<Respuesta> obtenerRespuestasPorFiltro(Estudiante estudiante, Date fecha, Pageable pageable);

    public List<IRespuestaProyeccion> guardarRespuestaEstudiante(Estudiante estudiante);

    IDatosaGraficarDTO graficarRespuestas(Estudiante estudiante, Date fecha);
}

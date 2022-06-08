package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Respuesta;

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

  List<Respuesta> obtenerRespuestaPorFecha(Date fecha);

  Respuesta obtenerRespuestaPorFechaYEstudiante(Date fecha, int idusuario);

  Respuesta saveRespuesta(Respuesta respuesta);

    List<Respuesta> obtenerRespuestasPorFiltro(Estudiante estudiante, Date fecha);
}

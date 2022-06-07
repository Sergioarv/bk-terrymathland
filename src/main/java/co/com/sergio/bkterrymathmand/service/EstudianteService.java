package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Estudiante;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:32
 **/
public interface EstudianteService {

    List<Estudiante> getAllEstudiantes();

    Estudiante agregarEstudiante(Estudiante estudiante);

    Estudiante estudianteByNombre(String nombre);

    Estudiante guardarRespuesta(Estudiante estudiante);

    List<Estudiante> filtrarEstudiante(String nombre, Date fecha);

    Estudiante actualizarEstudiante(Estudiante estudiante);

    boolean eliminarEstudiante(Estudiante estudiante);
}

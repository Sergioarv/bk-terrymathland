package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 11:44
 **/
public interface EstudianteService {

    List<Estudiante> getAllEstudiantes();

    Estudiante agregarEstudiante(Estudiante estudiante);

    IEstudianteProyeccion estudianteByDocumento(String nombre);

    Page<Estudiante> filtrarEstudiante(String nombre, Date fecha, PageRequest pageable);

    Estudiante actualizarEstudiante(Estudiante estudiante);

    boolean eliminarEstudiante(Estudiante estudiante);

    Estudiante obtenerEstudiantePorNombre(String nombre);

    List<IEstudianteProyeccion> obtenerIdyNombreEstudiantes();
}

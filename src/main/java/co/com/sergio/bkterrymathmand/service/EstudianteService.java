package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:32
 **/
public interface EstudianteService {

    public List<Estudiante> getAllEstudiantes();

    public Estudiante estudianteByNombre(String nombre);

    public List<Opcion> allOpciones();

    public List<Opcion> opcionesQuery(String idpregunta);

    public Estudiante saveEstudiante(Estudiante estudiante);
}

package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Docente;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:32
 **/
public interface DocenteService {

    List<Docente> obtenerDocentes();

    Docente docentePorNombre(String nombre);

    Docente agregarDocente(Docente docente);

    List<Docente> filtrarEstudiante(String nombre, String correo);

    Docente editarDocente(Docente docente);

    boolean eliminarDocente(Docente docente);
}

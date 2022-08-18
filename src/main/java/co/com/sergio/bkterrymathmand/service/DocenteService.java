package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:52
 **/
public interface DocenteService {

    List<Docente> obtenerDocentes();

    Docente docentePorNombre(String nombre);

    Docente agregarDocente(Docente docente);

    Page<Docente> filtrarDocente(String nombre, String correo, PageRequest pageable);

    Docente editarDocente(Docente docente);

    boolean eliminarDocente(Docente docente);
}

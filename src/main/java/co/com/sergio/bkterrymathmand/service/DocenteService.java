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

    public List<Docente> getAllDocente();

    public Docente docenteByNombre(String nombre);

    public Docente saveDocente(Docente docente);
}

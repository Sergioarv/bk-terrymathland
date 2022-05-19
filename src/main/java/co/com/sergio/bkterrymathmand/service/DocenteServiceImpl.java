package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 11:01
 **/

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public List<Docente> getAllDocente() {
        return docenteRepository.findAll();
    }

    @Override
    public Docente docenteByNombre(String nombre) {
        return docenteRepository.docenteByNombre(nombre);
    }

    @Override
    public Docente saveDocente(Docente docente) {
        return docenteRepository.save(docente);
    }
}

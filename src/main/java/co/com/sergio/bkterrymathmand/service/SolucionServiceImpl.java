package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 15:07
 **/

@Service
public class SolucionServiceImpl implements SolucionService {

    @Autowired
    private SolucionRepository solucionRepository;

    @Override
    public List<Solucion> getAllSolucion() {
        return solucionRepository.findAll();
    }
}

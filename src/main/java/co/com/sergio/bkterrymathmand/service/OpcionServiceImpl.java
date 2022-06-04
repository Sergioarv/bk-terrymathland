package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.repository.OpcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 4/06/2022 11:29
 **/

@Service
public class OpcionServiceImpl implements OpcionService {

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Opcion> obtenerOpcionesDePregunta(int idpregunta) {
        return opcionRepository.obtenerOpcionesDePregunta(idpregunta);
    }
}

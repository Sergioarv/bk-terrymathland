package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:30
 **/

@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public List<Respuesta> getAllRespuesta() {
        return respuestaRepository.findAll();
    }

    @Override
    public List<Respuesta> getRespuestaByFecha(Date fecha) {
        return respuestaRepository.findRespuestaByFecha(fecha);
    }

    @Override
    public Respuesta getRespuestaByFechaAndUsuario(Date fecha, int idusuario) {
        return respuestaRepository.findRespuestaByFechaAndUsuario(fecha, idusuario);
    }

    @Override
    public Respuesta saveRespuesta(Respuesta respuesta) {

        Respuesta data = null;

        if(data != null ){
            respuesta.setIdrespuesta(data.getIdrespuesta());
        }

        respuestaRepository.save(respuesta);

        return null;
    }
}

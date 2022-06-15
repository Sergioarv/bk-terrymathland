package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.repository.CartillaRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartillaServiceImpl implements CartillaService{

    @Autowired
    private CartillaRepository cartillaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    public List<Cartilla> obtenerCartillas() {
        return cartillaRepository.findAll();
    }

    @Override
    public List<Pregunta> obtenerPreguntas(int idcartilla) {

        Cartilla cartilla = cartillaRepository.findById(idcartilla).orElse(null);

        if(cartilla != null){
            return cartilla.getPreguntas();
        }

        return null;
    }

    @Override
    public List<Pregunta> filtrarPregunta(String idcartilla) {

        List<Pregunta> listResult = null;

        if(idcartilla != null ){
            listResult = obtenerPreguntas(Integer.parseInt(idcartilla));
        } else {
            listResult = preguntaRepository.findAll();
        }
        return listResult;
    }
}

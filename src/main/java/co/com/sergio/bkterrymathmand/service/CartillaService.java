package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;

import java.util.List;

public interface CartillaService {

    List<Cartilla> obtenerCartillas();

    List<Pregunta> obtenerPreguntas(int idcartilla);

    List<Pregunta> filtrarPregunta(String idcartilla);

    Boolean actualizarCartilla(Cartilla cartilla);

    Cartilla crearCartilla(Cartilla cartilla);
}

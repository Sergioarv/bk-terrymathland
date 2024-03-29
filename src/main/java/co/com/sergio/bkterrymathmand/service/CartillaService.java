package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.dto.ICartillaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 12/06/2022 15:08
 **/
public interface CartillaService {

    List<Cartilla> obtenerCartillas();

    List<Pregunta> obtenerPreguntas(int idcartilla);

    Page<Pregunta> filtrarPregunta(String idcartilla, Pageable pageable);

    Boolean actualizarCartilla(Cartilla cartilla);

    Cartilla crearCartilla(Cartilla cartilla);

    Boolean eliminarCartilla(Cartilla cartilla);

    List<ICartillaProyeccion> listarCartillas();

}

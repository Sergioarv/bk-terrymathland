package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:30
 **/


public interface PreguntaService {

    List<Pregunta> obtenerPreguntas();

    Page<Pregunta> filtrarPregunta(String id, String enunciado, Pageable pageable);

    Pregunta editarPregunta(Pregunta pregunta, MultipartFile file);

    Pregunta crearPregunta(Pregunta preguntaJson, MultipartFile file) throws Exception;

    Boolean eliminarPregunta(Pregunta pregunta) throws Exception;
}

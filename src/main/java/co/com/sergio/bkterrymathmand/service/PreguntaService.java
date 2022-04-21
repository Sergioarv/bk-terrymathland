package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Pregunta;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:30
 **/


public interface PreguntaService {

    public List<Pregunta> findAllPregunta();
}
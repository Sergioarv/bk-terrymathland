package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.repository.OpcionRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:31
 **/

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> findAllPregunta() {
        return preguntaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPorId(int id) {
        return preguntaRepository.filtrarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPor(String filtro) {
        return preguntaRepository.filtrarPor(filtro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPorIdOEnunciado(int id, String enunciado) {
        return preguntaRepository.filtrarPorIdOEnunciado(id, enunciado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPregunta(String id, String enunciado) {

        List<Pregunta> listResult;

        if(id != null && enunciado != null){
            listResult = filtrarPorIdOEnunciado(Integer.parseInt(id), enunciado);
        }else if(id != null){
            listResult = filtrarPorId(Integer.parseInt(id));
        }else if( enunciado != null){
            listResult = filtrarPor(enunciado);
        }else{
            listResult = findAllPregunta();
        }

        return listResult;
    }

    @Override
    public Pregunta editarPregunta(Pregunta pregunta) {

        List<Opcion> result;
        Pregunta newPregunta = null;
        result = opcionRepository.saveAll(pregunta.getOpciones());

        if(result != null){
            newPregunta = preguntaRepository.save(pregunta);
        }else{
            newPregunta.setOpciones(null);
        }

        return newPregunta;
    }
}
